/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.test.framework;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The dispatcher of queries to drill. There are a variety of interfaces via
 * which to dispatch queries.
 * 
 * @author Zhiyong Liu
 * 
 */
public class GenericQueryDispatcher {
  protected static final Logger LOG = Logger.getLogger(Utils
      .getInvokingClassName());
  private String schema;

  /**
   * Constructor with the schema name
   * 
   * @param schema
   *          name of schema to use
   */
  public GenericQueryDispatcher(String schema) {
    this.schema = schema;
  }

  /**
   * Submits query(ies) via CLI
   * 
   * @param queryFileName
   *          name of file to use for query submission
   * @throws IOException
   * @throws InterruptedException
   */
  public void dispatchQueriesCLI(String sqllineCommand, String queryFileName)
      throws IOException, InterruptedException {
    // TODO: need to parameterize the command line; need to finalize
    // treatment of schemas
    String command = sqllineCommand
        + " -n admin -p admin -u jdbc:drill:schema=" + schema + " -f "
        + queryFileName;
    LOG.info("Executing " + command + ".");
    Runtime.getRuntime().exec(command).waitFor();
  }

  /**
   * Submits query via JDBC
   * 
   * @param queryFileName
   *          name of query file
   * @param statement
   *          sql statement to execute the query with
   * @return Map of query results and their occurrences
   * @throws Exception
   */
  public Map<String, Integer> dispatchQueryJDBC(String query,
      Statement statement) throws Exception {
    Map<String, Integer> map = new HashMap<String, Integer>();
    ResultSet resultSet = null;
    try {
      LOG.info("Submitting query:\n" + query);
      resultSet = statement.executeQuery(query);
      int columnCount = resultSet.getMetaData().getColumnCount();
      while (resultSet.next()) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= columnCount; i++) {
          try {
            builder.append(new String(resultSet.getBytes(i)) + "\t");
          } catch (Exception e) {
            builder.append(resultSet.getObject(i) + "\t");
          }
        }
        String entry = builder.toString().trim();
        if (map.containsKey(entry)) {
          map.put(entry, map.get(entry) + 1);
        } else {
          map.put(entry, 1);
        }
      }
      return map;
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
    }
  }

  /**
   * Executes a JDBC Query and iterates through the resultSet
   * 
   * @param connectionUrl
   * @param queryFileName
   * @return
   */
  public boolean executeQueryJDBC(String query, String queryFileName, Statement statement) {
    boolean status = true;
    ResultSet resultSet = null;
    long startTime = 0l;
    long connTime = Long.MIN_VALUE;
    long executeTime = Long.MIN_VALUE;
    long firstRowFetchTime = Long.MIN_VALUE;
    long endTime = Long.MIN_VALUE;
    long lastRowFetchTime = Long.MIN_VALUE;
    long rowCount = 0l;
    int columnCount = 0;
    try {
      LOG.info("Extracted Query from : " + queryFileName);
      String basicFileName = (new File(queryFileName)).getName();
      String queryLabel = basicFileName.subSequence(0,
          basicFileName.lastIndexOf(".q")).toString();
      LOG.info("Executing Query : " + queryLabel);
      startTime = System.currentTimeMillis();
      // Time to Connect
      connTime = System.currentTimeMillis();
      LOG.info("Connect Time: " + ((connTime - startTime) / 1000f) + " sec");
      resultSet = statement.executeQuery(query);
      // Time to Execute
      executeTime = System.currentTimeMillis();
      LOG.info("Execute Time: " + ((executeTime - connTime) / 1000f) + " sec");
      columnCount = resultSet.getMetaData().getColumnCount();
      while (resultSet.next()) {
        if (rowCount == 0)
          firstRowFetchTime = System.currentTimeMillis();
        rowCount++;
        lastRowFetchTime = System.currentTimeMillis();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      LOG.error(e.getMessage());
      status = false;
    } finally {
      endTime = System.currentTimeMillis();
      try {
        LOG.info("Closing connections");
        if (resultSet != null) {
          resultSet.close();
        }
      } catch (SQLException e) {
        LOG.error("[ERROR] During close: " + e.getMessage());
        status = false;
      }
      if (!status)
        LOG.error("Last row was fetched at " + new Date(lastRowFetchTime)
            + " [" + ((endTime - lastRowFetchTime) / 1000f) + " secs ago]");
      LOG.info("Time to fetch 1st row : "
          + ((firstRowFetchTime - executeTime) / 1000f) + " sec");
      LOG.info("Fetched " + rowCount + " rows with " + columnCount
          + " columns in " + ((endTime - executeTime) / 1000f) + " sec");
      LOG.info("Fetch Rate: " + (rowCount * 1000f / (endTime - executeTime))
          + " rows/sec ");
    }
    LOG.info("Total Time: " + (System.currentTimeMillis() - startTime) / 1000f
        + " sec ");
    return status;
  }
}