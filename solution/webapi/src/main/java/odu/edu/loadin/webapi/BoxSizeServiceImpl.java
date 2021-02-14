/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package odu.edu.loadin.webapi;


import odu.edu.loadin.common.*;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BoxSizeServiceImpl implements BoxService {


    public BoxSizeServiceImpl() {

    }


    @Override
    public ArrayList<BoxSize> getBoxSizes() throws SQLException {

        //we get a connection here
         Connection conn = DatabaseConnectionProvider.getLoadInSqlConnection();
         PreparedStatement statement = conn.prepareStatement("SELECT * FROM BOX_SIZES");
        ArrayList<BoxSize> results = Clippy.getResults(statement, () -> new BoxSize());
        conn.close();

        return results;
    }



}
