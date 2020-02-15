package com.keyrus.key.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class AzureUtils {

    // Properties File
    private Properties prop = new Properties();
    private String dir = System.getProperty("user.dir");

    // Utilities
    private Base64Utils base64;

    // EFA Execution Information
    private String testName;
    private String expectedResult;
    private String testResult;

    // VSTS Execution Information
    private String organizationName;
    private String projectName;
    private String testPlanID;
    private String testSuitID;
    private String authorizationToken;
    private String azureAPIsURL;

    public AzureUtils() throws Exception {
        // Utilities
        this.base64 = new Base64Utils();

        // Properties File
        prop.load(new FileInputStream(dir + "\\properties\\azure.properties"));
    }

    public void mountAzureEnvironment(){

        System.out.println("Method: Mounting Azure Environment");

        this.organizationName = prop.getProperty("dbo.organization");
        this.projectName = prop.getProperty("dbo.project");
        this.testPlanID = prop.getProperty("dbo.testPlanID");
        this.testSuitID = prop.getProperty("dbo.testSuitID");
        this.authorizationToken = getEncodedToken();
        this.azureAPIsURL = mountAzureURL();
    }

    public String mountAzureURL(){

        System.out.println("Method: Mounting Azure APIs URL");

        String url = "https://dev.azure.com/" + this.organizationName + "/" + this.projectName + "/_api";
        return url;
    }

    public void getExecutionInformation() {

        System.out.println("Method: Get Execution Information");

        this.testName = "genericTest";
        this.expectedResult = "Passed";
        this.testResult = "Passed";
    }

    public String getEncodedToken() {

        System.out.println("Method: Get encoded token");

        String user = prop.getProperty("dbo.accountVSTS");
        String token = prop.getProperty("dbo.accessToken");
        String userToken = user + ":" + token;

        String encodedUserToken = base64.encode(userToken);

        return encodedUserToken;
    }

    public void integrationVSTS() throws Exception {

        if(prop.getProperty("dbo.runVSTSscript").equals("no")) {

            System.out.println("\n===============================");
            System.out.println("No Integration with Azure DevOps");
            System.out.println("================================\n");

        } else {

            System.out.println("\n============================");
            System.out.println("Integration with Azure DevOps");
            System.out.println("==============================\n");

            // Get VSTS Execution Information
            this.mountAzureEnvironment();

            System.out.println("\n--");
            System.out.println("Organization: " + this.organizationName);
            System.out.println("Project: " + this.projectName);
            System.out.println("Plan ID: " + this.testPlanID);
            System.out.println("Suit ID: " + this.testSuitID);
            System.out.println("Will overwrite tests? " + prop.getProperty("dbo.overwriteResults"));
            System.out.println("URL: " + this.azureAPIsURL);
            System.out.println("--\n");

            // Get Execution Information
            this.getExecutionInformation();

            // Get Test Case ID
            String testCaseId = getTestCaseId();

            // Get Test Case Point ID
            int testCasePointId = (int) getTestPointId();

            // Validate Execution Status
            int statusVSTS = validateStatus(testResult);

            // Set Status on VSTS Test Case
            setVSTSstatus(testCasePointId, statusVSTS);

            // Upload Evidence on VSTS
            String uploadedEvidenceURL = postEvidenceOnVSTS();

            // Patch Evidence on VSTS Test Case
            patchEvidenceToTestCase(testCaseId, uploadedEvidenceURL);

        }
    }

    private void patchEvidenceToTestCase(String testCaseId, String uploadedEvidenceURL) throws Exception {

        System.out.println("\nAPI: Patch evidence to test case");
        System.out.println("--");

        String evidenceDocName = testName + ".docx";

        URL url = new URL(this.azureAPIsURL + "s/wit/workitems/" + testCaseId + "?api-version=5.0");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json-patch+json");
        conn.setRequestProperty("Authorization", "Basic " + authorizationToken);
        String input = "[{\"op\":\"add\",\"path\":\"/relations/-\",\"value\":{\"rel\":\"AttachedFile\",\"url\":\""
                + uploadedEvidenceURL + "\",\"attributes\":{\"comment\":\"Added using API\",\"name\": \""
                + evidenceDocName + "\"}}}]";
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        String resultIntegration = "" + conn.getResponseCode();
        System.out.println("Response code: " + resultIntegration);
        System.out.println("--");
        conn.disconnect();
    }

    public byte[] readBytesFromFile(File file) {
        System.out.println("Method: Read bytes from file");

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            bytesArray = new byte[(int) file.length()];

            // read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }

    public File getDocEvidenceFile() {
        System.out.println("Method: Get evidence file");

        // File Path
        String filePath = System.getProperty("user.dir") + "\\evidence\\genericTest.docx";

        // Get 'output' folder's path
        // String filePath = System.getProperty("user.dir") + "\\" + "output" + "\\";

        // Build
        // filePath += efa.getExecutionInfo().getExecutionTimestamp() + "\\";
        // filePath += efa.getExecutionInfo().getTestSuite() + "\\";
        // filePath += efa.getExecutionInfo().getTestName() + "\\";
        // filePath += efa.getExecutionInfo().getExecutionName() + "\\";
        // filePath += efa.getExecutionInfo().getExecutionName() + ".docx";

        File file = new File(filePath);

        return file;
    }

    public String postEvidenceOnVSTS() throws Exception {
        System.out.println("\nAPI: Post evidence on VSTS");
        System.out.println("--");

        // Get ".docx" file with the evidences
        File file = getDocEvidenceFile();

        // Convert File to "byte[]"
        byte[] bFile = readBytesFromFile(file);

        URL url = new URL( this.azureAPIsURL + "s/wit/attachments?api-version=5.0");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic " + authorizationToken);
        OutputStream os = conn.getOutputStream();
        os.write(bFile);
        os.flush();

        StringBuilder result = new StringBuilder();
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        JSONParser jsonParser = new JSONParser();
        Object object;
        object = jsonParser.parse(result.toString());
        JSONObject jsonObject = (JSONObject) object;
        String uploadedEvidenceURL = (String) jsonObject.get("url");
        System.out.println("Evidence URL: " + uploadedEvidenceURL);
        int resultIntegration = conn.getResponseCode();
        System.out.println("Response code: " + resultIntegration);
        System.out.println("--");
        conn.disconnect();
        return uploadedEvidenceURL;
    }

    public String getTestCaseId() throws Exception {
        System.out.println("\nAPI: Get test case id");
        System.out.println("--");

        //String targetTestCase = this.testName;
        String targetTestCase = "genericTest";

        String testCaseId = "";

        StringBuilder result = new StringBuilder();
        URL url = new URL(this.azureAPIsURL + "s/test/Plans/" + testPlanID + "/Suites/"
                + testSuitID + "/points?api-version=5.0");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic " + authorizationToken);
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        JSONParser jsonParser = new JSONParser();
        Object object;
        object = jsonParser.parse(result.toString());
        JSONObject jsonObject = (JSONObject) object;
        JSONArray variable = (JSONArray) jsonObject.get("value");
        for (int i = 0; i < variable.size(); i++) {
            JSONObject selectedVariable = (JSONObject) variable.get(i);
            JSONObject selectedVariableTestCase = (JSONObject) selectedVariable.get("testCase");
            testCaseId = (String) selectedVariableTestCase.get("id");
            JSONArray workItemProperties = (JSONArray) selectedVariable.get("workItemProperties");
            JSONObject selectedworkItemProperties = (JSONObject) workItemProperties.get(2);
            JSONObject workItem = (JSONObject) selectedworkItemProperties.get("workItem");
            String testCase = (String) workItem.get("value");

            if (testCase.equals(targetTestCase)) {
                System.out.println("Test Case " + testCase + " found!");
                System.out.println("Test Case ID: " + testCaseId);
                break;
            } else {
                testCaseId = "";
            }
        }

        if (testCaseId.equals("")) {
            System.out.println("Test Case: " + targetTestCase + " not found!");
        }

        int resultIntegration = conn.getResponseCode();
        System.out.println("Response code: " + resultIntegration);
        System.out.println("--");
        conn.disconnect();

        return testCaseId;
    }

    // ---------------------
    //  Status
    // ---------------------
    // 0 - Active
    // 1 - Active(?)
    // 2 - Passed
    // 3 - Failed
    // 4 - Inconclusive
    // 5 - Timeout
    // 6 - Aborted
    // 7 - Blocked
    // 8 - Active(?)
    // 9 - Warning
    // 10 - Error
    // 11 - Not Applicable
    // 12 - Active(?)
    // 13 - Active(?)
    // 14 - Active(?)
    // 15 - Active(?)
    // ---------------------
    public void setVSTSstatus(int testPointId, int status) throws Exception {

        System.out.println("\nAPI: Set VSTS status");
        System.out.println("--");

        try {
            if (!(testPointId == -1)) {
                URL url = new URL(this.azureAPIsURL + "/_testManagement/BulkMarkTestPoints?api-version=5.0");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Basic " + authorizationToken);
                String input = "{\"planId\":" + testPlanID + ",\"suiteId\":" + testSuitID + ",\"testPointIds\":["
                        + testPointId + "],\"outcome\":" + status + "}";
                String testStatus = "" + status;
                if (testStatus.equals("2")) {
                    System.out.println("Test Result: Passed");
                } else {
                    System.out.println("Test Result: Failed");
                }
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();
                int resultIntegration = conn.getResponseCode();
                System.out.println("Response code: " + resultIntegration);
                conn.disconnect();
            } else {
                System.out.println("Test Case already executed in VSTS");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

    public long getTestPointId() throws Exception {

        System.out.println("\nAPI: Get test point id");
        System.out.println("--");

        long testCaseId = 0;

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(this.azureAPIsURL + "s/test/Plans/" + testPlanID
                    + "/Suites/" + testSuitID + "/points?api-version=5.0");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + authorizationToken);
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            JSONParser jsonParser = new JSONParser();
            Object object;
            object = jsonParser.parse(result.toString());
            JSONObject jsonObject = (JSONObject) object;
            JSONArray variable = (JSONArray) jsonObject.get("value");
            for (int i = 0; i < variable.size(); i++) {
                JSONObject selectedVariable = (JSONObject) variable.get(i);
                long pointId = (Long) selectedVariable.get("id");
                String lastResultState = (String) selectedVariable.get("lastResultState");
                JSONArray workItemProperties = (JSONArray) selectedVariable.get("workItemProperties");
                JSONObject selectedworkItemProperties = (JSONObject) workItemProperties.get(2);
                JSONObject workItem = (JSONObject) selectedworkItemProperties.get("workItem");
                String testCase = (String) workItem.get("value");
                if (testCase.equals(testName)) {
                    System.out.println("Test Case " + testName + " found!");
                    if (lastResultState.equals("Completed") && prop.getProperty("dbo.overwriteResults").equals("no")) {
                        System.out.println("Test Case already \"Completed\", status won't be overwritten due properties set an \"no\"");
                        testCaseId = -1;
                    } else {
                        testCaseId = pointId;
                        System.out.println("Test Case Point ID: " + pointId);
                    }
                }
                else {
                    System.out.println("Test Case " + this.testName + " not found!");
                }
            }
            int resultIntegration = conn.getResponseCode();
            System.out.println("Response code: " + resultIntegration);
            System.out.println("--");
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testCaseId;
    }

    /**
     * Method to validate message and return test result
     *
     * @param msg
     * @throws Exception
     * @author mgarcdia
     */
    public int validateStatus(String msg) {

        System.out.println("\nMethod: Validate test final result");

        if (msg.equalsIgnoreCase(expectedResult)) {
            return 2;
        } else {
            return 3;
        }
    }
}