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


    private Connection connection = null;

    @Override
    public ExpertArticle getExpertArticles(String Keyword){


        try(Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection()){ //this is called a try with resources and with java 1.8

            //this will auto-close the connection
            PreparedStatement statement = conn.prepareStatement("SELECT KEYWORD, CONTENT, TITLE FROM EXPERT_TIP");
            //statement.setString(1, Keyword);

            ResultSet resultSet = statement.executeQuery();

            ArrayList<ExpertArticle> expertArticle = new ArrayList<>();

            while(resultSet.next()){
                ExpertArticle article = new ExpertArticle();

                article.setKeyword(resultSet.getString("KEYWORD"));
                article.setArticleContent(resultSet.getString("CONTENT"));
                article.setArticleTitle(resultSet.getString("TITLE"));
                expertArticle.add(article);

            }

            statement.close();



            //this is more of a transparent method.  person who is performing the query can decide how it gets mapped back to
            //individual objects
            /*    ExpertArticle results = StatementHelper.getResults(statement, (ResultSet rs) -> {
                    return mapStandardArticleResult(rs);
                }).stream().findFirst().orElse(null);

             */
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

    private ExpertArticle mapStandardArticleResult(ResultSet rs) throws SQLException {
        ExpertArticle r = new ExpertArticle();
        r.setKeyword(rs.getString("KEYWORD"));
        r.setArticleContent(rs.getString("CONTENT"));
        r.setArticleTitle(rs.getString("TITLE"));
        r.setCreatedAt(rs.getDate("CREATED_AT"));
        r.setUpdatedAt(rs.getDate("UPDATED_AT"));
        r.setVisualFile(rs.getString("IMAGE"));
        return r;
    }



    private ExpertArticle startLucene(String keyword, ArrayList<ExpertArticle> expertArticles) throws IOException, ParseException {

        ExpertArticle results = new ExpertArticle();

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
            addDoc(w, expertArticle.getKeyword(), expertArticle.getArticleTitle(), expertArticle.getArticleContent());
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


        // printing off the result to the console.
        //Log.d("test","Found " + hits.length + " hits.");
        for(int i=0; i < hits.length; ++i) {

            int docId = hits[i].doc;
            Document doc = searcher.doc(docId);
            //Log.d("test",(i + 1) + ". Keyword: " + doc.get("keyword") + "\tExpect Tip Title: " + doc.get("title") +
              //      "\tExpect Tip Article: " + doc.get("article"));

            results.setKeyword(doc.get("keyword"));
            results.setArticleTitle(doc.get("title"));
            results.setArticleContent(doc.get("article"));

        }

        reader.close();


        return results;
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

    private static void addDoc(IndexWriter w, String keyword, String title, String article) throws IOException {
        Document doc = new Document();

        // using TextField that is indexed and tokenized, without term vectors.
        doc.add(new TextField("keyword", keyword, Field.Store.YES));

        // using a string field for title because we don't want it tokenized
        doc.add(new StringField("article", article, Field.Store.YES));

        // using a string field for title because we don't want it tokenized
        doc.add(new StringField("title", title, Field.Store.YES));
        w.addDocument(doc);
    }


}
