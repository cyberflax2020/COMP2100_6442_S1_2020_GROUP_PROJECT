package com.example.myapplication111;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpPost extends AsyncTask<String, String, String> {
    private final static String Tag = "Log test";





    @Override
    protected String doInBackground(String... strings) {
        String returnvalue = "";
        try {
            if (strings[0].equals("Register") == true) {
                returnvalue = this.LoginOrRegisterPost(strings[1], strings[2], "47.75.60.118", "user register");
            }
            else if (strings[0].equals("Login") == true) {
                returnvalue = this.LoginOrRegisterPost(strings[1], strings[2], "47.75.60.118", "user login");
            }
            else if (strings[0].equals("Save User Class") == true) {
                returnvalue = this.SaveUserClassPost(strings[1], strings[2], "47.75.60.118", "save user class");
            }
            else if (strings[0].equals("Get User Class") == true) {
                returnvalue = this.UsernamePost(strings[1], "47.75.60.118", "get user class");
            }
            else if (strings[0].equals("Publish Survey") == true) {
                returnvalue = this.UsernamePost(strings[1], "47.75.60.118", "publish survey");
            }
            else if (strings[0].equals("Delete Survey") == true) {
                returnvalue = this.SidPost(strings[1], "47.75.60.118", "delete survey");
            }
            else if (strings[0].equals("Engage Survey") == true) {
                returnvalue = this.UsernameSidPost(strings[1], strings[2], "47.75.60.118", "engage survey");
            }
            else if (strings[0].equals("Search Survey") == true) {
                returnvalue = this.SidPost(strings[1], "47.75.60.118", "search survey");
            }
        } catch (MalformedURLException m) {
            m.printStackTrace();
        }

        return returnvalue;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(Tag, "onPostExecute");
    }

    private String LoginOrRegisterPost(String username, String password, String host, String property) throws MalformedURLException {
        String result = "";
        HttpURLConnection conn = null;

        try {       //send a request
            URL url = new URL("http://" + host + ":8080/web-test/HttpApp");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authentication", property);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            StringBuilder str_builder = new StringBuilder();
            str_builder.append("username=").append(URLEncoder.encode(username, "UTF-8")).append("&");
            str_builder.append("password=").append(URLEncoder.encode(password, "UTF-8")).append("&");
            output.writeBytes(str_builder.toString());
            output.flush();
            output.close();
        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        try {
            InputStream input = conn.getInputStream();
            int status = conn.getResponseCode();
            Log.d(Tag, String.valueOf(status));

            if (input != null) {
                InputStreamReader reader = new InputStreamReader(input, "UTF-8");
                BufferedReader in = new BufferedReader(reader);
                String newline = "";
                while ((newline = in.readLine()) != null) {
                    result += (newline);
                }
            }
            else {
                result = "Server no response";
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

        return result;
    }

    private String SaveUserClassPost(String username, String toSave, String host, String property) throws MalformedURLException {
        String result = "";
        HttpURLConnection conn = null;

        try {       //send a request
            URL url = new URL("http://" + host + ":8080/web-test/HttpApp");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authentication", property);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            StringBuilder str_builder = new StringBuilder();
            str_builder.append("username=").append(URLEncoder.encode(username, "UTF-8")).append("&");
            str_builder.append("content=").append(URLEncoder.encode(toSave, "UTF-8")).append("&");
            output.writeBytes(str_builder.toString());
            output.flush();
            output.close();
        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        try {
            InputStream input = conn.getInputStream();
            int status = conn.getResponseCode();
            Log.d(Tag, String.valueOf(status));

            if (input != null) {
                InputStreamReader reader = new InputStreamReader(input, "UTF-8");
                BufferedReader in = new BufferedReader(reader);
                String newline = "";
                while ((newline = in.readLine()) != null) {
                    result += (newline);
                }
            }
            else {
                result = "Server no response";
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

        return result;
    }

    private String UsernamePost(String username, String host, String property) throws MalformedURLException {
        String result = "";
        HttpURLConnection conn = null;

        try {       //send a request
            URL url = new URL("http://" + host + ":8080/web-test/HttpApp");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authentication", property);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            StringBuilder str_builder = new StringBuilder();
            str_builder.append("username=").append(URLEncoder.encode(username, "UTF-8")).append("&");
            output.writeBytes(str_builder.toString());
            output.flush();
            output.close();
        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        try {
            InputStream input = conn.getInputStream();
            int status = conn.getResponseCode();
            Log.d(Tag, String.valueOf(status));

            if (input != null) {
                InputStreamReader reader = new InputStreamReader(input, "UTF-8");
                BufferedReader in = new BufferedReader(reader);
                String newline = "";
                while ((newline = in.readLine()) != null) {
                    result += (newline);
                }
            }
            else {
                result = "Server no response";
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

        return result;
    }

    private String SidPost(String sid, String host, String property) throws MalformedURLException {
        String result = "";
        HttpURLConnection conn = null;

        try {       //send a request
            URL url = new URL("http://" + host + ":8080/web-test/HttpApp");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authentication", property);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            StringBuilder str_builder = new StringBuilder();
            str_builder.append("surveyid=").append(URLEncoder.encode(sid, "UTF-8")).append("&");
            output.writeBytes(str_builder.toString());
            output.flush();
            output.close();
        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        try {
            InputStream input = conn.getInputStream();
            int status = conn.getResponseCode();
            Log.d(Tag, String.valueOf(status));

            if (input != null) {
                InputStreamReader reader = new InputStreamReader(input, "UTF-8");
                BufferedReader in = new BufferedReader(reader);
                String newline = "";
                while ((newline = in.readLine()) != null) {
                    result += (newline);
                }
            }
            else {
                result = "Server no response";
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

        return result;
    }

    private String UsernameSidPost(String username, String sid, String host, String property) throws MalformedURLException {
        String result = "";
        HttpURLConnection conn = null;

        try {       //send a request
            URL url = new URL("http://" + host + ":8080/web-test/HttpApp");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authentication", property);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            StringBuilder str_builder = new StringBuilder();
            str_builder.append("username=").append(URLEncoder.encode(username, "UTF-8")).append("&");
            str_builder.append("surveyid=").append(URLEncoder.encode(sid, "UTF-8")).append("&");
            output.writeBytes(str_builder.toString());
            output.flush();
            output.close();
        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        try {
            InputStream input = conn.getInputStream();
            int status = conn.getResponseCode();
            Log.d(Tag, String.valueOf(status));

            if (input != null) {
                InputStreamReader reader = new InputStreamReader(input, "UTF-8");
                BufferedReader in = new BufferedReader(reader);
                String newline = "";
                while ((newline = in.readLine()) != null) {
                    result += (newline);
                }
            }
            else {
                result = "Server no response";
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

        return result;
    }
}
