/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.aliyuncs.batchcompute.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by guangchun.luo on 15/4/16.
 */
public class FileLoader {

    public static String loadFile(String path) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {

            String pathStr = FileLoader.class.getClassLoader().getResource(path).getPath();

            FileReader fr = new FileReader(new File(pathStr));

            br = new BufferedReader(fr);

            String str = "";
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    public static Properties loadProperties(String[] path) {
        Properties prop = new Properties();
        for (int i = 0, len = path.length; i < len; i++) {
            System.out.println("load properties:" + path[i]);
            InputStream  in = null;

            if(path[i].startsWith("/") || path[i].matches("^[A-Z]:\\\\")  ){
                try {
                    in = new FileInputStream(new File(path[i]));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                in =
                        FileLoader.class.getClassLoader()
                                .getResourceAsStream(path[i]);
            }

            try {
                prop.load(in);
                return prop;
            } catch (IOException e) {
                continue;
            }
        }
        return prop;
    }

    public static void main(String[] args) throws FileNotFoundException {

        String s = FileLoader.loadFile("resources/listJobs.json");
        System.out.println(s);

        String[] arr = new String[]{
                "resources/config.properties"
        };
        Properties p = FileLoader.loadProperties(arr);
        System.out.println(p);
    }
}
