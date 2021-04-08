package odu.edu.loadin.webapi;

import odu.edu.loadin.common.*;
import odu.edu.loadin.helpers.StatementHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import javax.ws.rs.core.Response;

public class ExpertArticleServiceImpl implements ExpertArticleService {

    @Override
    public ArrayList<ExpertArticle> getExpertArticles(String Keyword){


        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){ //this is called a try with resources and with java 1.8

            //this will auto-close the connection
            PreparedStatement statement = conn.prepareStatement("SELECT KEYWORD, CONTENT, TITLE, VIDEO FROM EXPERT_TIP");
            //statement.setString(1, Keyword);

            ResultSet resultSet = statement.executeQuery();

            ArrayList<ExpertArticle> expertArticle = new ArrayList<>();

            while(resultSet.next()){
                ExpertArticle article = new ExpertArticle();

                article.setKeyword(resultSet.getString("KEYWORD"));
                article.setArticleContent(resultSet.getString("CONTENT"));
                article.setArticleTitle(resultSet.getString("TITLE"));
                article.setVisualFile(resultSet.getString("VIDEO"));
                expertArticle.add(article);
            }

            statement.close(); //TODO: Is this needed? I didn't see where this service was close in the previous code. bad practice to leave open

            try {
                return startLucene(Keyword, expertArticle);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (SQLException ex){
            //TODO: exception logging
            System.out.println(ex);
        }
        return null;
    }

    /**
     * This is the main driver for the expert tips. The following function takes in the keyword and an array list of expert tips that will be parsed through.
     * @param keyword - passed in through the android application
     * @param expertArticles - generated from the database
     * @return - a single expert tip if a keyword finds a article
     * @throws IOException -
     * @throws ParseException -
     */
    private ArrayList<ExpertArticle> startLucene(String keyword, ArrayList<ExpertArticle> expertArticles) throws IOException, ParseException {

        // Specify the analyzer for tokenizing text.
        //    The same analyzer should be used for indexing and searching

        // StandardAnalyzer removes stop words from the input, which is a
        // great help when processing naturally occurring text. StandardAnalyzer
        // also converts text to lowercase (an important point to note because case
        // issues can lead to subtle bugs in application code when searching for data
        // indexed with StandardAnalyzer).
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // IndexWriter is the main user-facing class responsible for indexing
        // data in Lucene. IndexWriter is used for analyzing documents, opening
        // directories, and writing the data to directories.
        Directory index = new RAMDirectory(); //TODO: NEED TO REPLACE THIS SINCE IT IS DEPRECATED SEE API https://lucene.apache.org/core/8_8_1/core/index.html

        // Holds all the configuration that is used to create an IndexWriter.
        // Once IndexWriter has been created with this object, changes to this object will not affect the IndexWriter instance.
        // For that, use LiveIndexWriterConfig that is returned from IndexWriter.getConfig().
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //An IndexWriter creates and maintains an index.
        IndexWriter w = new IndexWriter(index, config);
        for (ExpertArticle expertArticle : expertArticles) {
            addDoc(w, expertArticle.getKeyword(), expertArticle.getArticleTitle(), expertArticle.getArticleContent(), expertArticle.getVisualFile());
        }

        w.close();

        // the quertString is the keyword we are matching. If you look at our addDoc function, the param named keyword is what we are searching.
        String queryString = keyword;

        // the "keyword" arg below specifies the default field to use when querying data. Currently I have three fields we could use: keyword, title, and article. Either of these can replace keyword
        Query query = new QueryParser("keyword", analyzer).parse(queryString);

        // limit of the amount of tips to display
        int hitsPerPage = 10;

        //used to read the underlying index using the Directory and corresponding abstractions.
        IndexReader reader = DirectoryReader.open(index);

        // IndexSearcher is the abstraction present in Lucene that executes
        // search over a single Lucene index. IndexSearcher is opened on top
        // of IndexReader, which is used to read the underlying index using
        // the Directory and corresponding abstractions.
        IndexSearcher searcher = new IndexSearcher(reader);

        //TopDocs is the representation of the top documents that match the given
        //query. They are a generic representation and do not necessarily depend
        //on the underlying algorithm used to calculate how the top documents
        //are calculated. TopDocs consist of two components: scoreDocs (the
        //documentIDs of the top N hits, where N was the requested value) and the
        //score of each of those hits.
        TopDocs docs = searcher.search(query, hitsPerPage);


        ScoreDoc[] hits = docs.scoreDocs;


        ArrayList<ExpertArticle> foundArticles = new ArrayList<ExpertArticle>();
        ExpertArticle results = new ExpertArticle();
        // looping through our results.
        if(hits.length >= 1) {
            for (int i = 0; i < hits.length; ++i) {
                ExpertArticle searched = new ExpertArticle();
                int docId = hits[i].doc;
                Document doc = searcher.doc(docId);

                //grabbing each expert tip and storing it into our ADT
                searched.setKeyword(doc.get("keyword"));
                searched.setArticleTitle(doc.get("title"));
                searched.setArticleContent(doc.get("article"));
                searched.setVisualFile(doc.get("video"));
                foundArticles.add(searched);
            }
        }
        else
        {
            results.setKeyword("");
            results.setArticleTitle("");
            results.setArticleContent("");

            try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){

                String addQuery = "INSERT INTO EXPERT_TIP_UNMATCHED_KEYWORDS(KEYWORD, CREATED_AT, UPDATED_AT)"
                        +" VALUES (?, NOW(), NOW() )";

                PreparedStatement insertStatement = conn.prepareStatement(addQuery);
                insertStatement.setString(1,keyword);
                System.out.println(insertStatement);
                insertStatement.executeUpdate();

            }
            catch (SQLException ex){

            }

        }

        reader.close();
        return foundArticles;
    }



    /**
     * Used to add items to the Document element.
     *
     * @param w - creates and maintains an index.
     * @param keyword - keyword we are searching for inside
     * @param title - title of the articles
     * @param article - the meat of the article
     * @throws IOException
     */

    private static void addDoc(IndexWriter w, String keyword, String title, String article, String video) throws IOException {
        Document doc = new Document();

        // using TextField that is indexed and tokenized, without term vectors.
        doc.add(new TextField("keyword", keyword, Field.Store.YES));

        // using a string field for title because we don't want it tokenized
        doc.add(new StringField("article", article, Field.Store.YES));

        // using a string field for title because we don't want it tokenized
        doc.add(new StringField("title", title, Field.Store.YES));

        doc.add(new StringField("video", video, Field.Store.YES));
        w.addDocument(doc);
    }


}
