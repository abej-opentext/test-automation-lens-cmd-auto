package com.opentext.bn.lens.cmd;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.opentext.bn.core.Datatable;
import com.opentext.bn.lens.CMD_POST;
//import com.opentext.bn.lens.cmd.utils.Report;

import io.restassured.response.Response;

/*
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
*/


public class BUCreate_Lens {
	
	
	Logger logger=Logger.getLogger(BUCreate_Lens.class.getName());

	
	static String currentDirectory=System.getProperty("user.dir");
	
	static String BaseDir=currentDirectory +"/Files/";
	
	
	public static String ENV;
	public static String NODE;
	public static String BaseURI;
	public static String RUNNABLE;
	public static String TestCaseId;
	public static String Scenario;
	public static String ScenarioType;
	
	public static String im_bu_id;
	public static String im_user_id;
	public static String im_service_instance_id;
	public static String im_community_id;
	public static String im_principal_type;
	public static String ContentType;
	
	public static String DBRetryCount;
	public static String DBQuerySleepTime;
	
	//Body parameters
	public static String name;
	public static String addressCounty;
	public static String addressCity;
	public static String addressCountryCode;
	public static String status;
	public static String currentLocale;
	public static String naicsCodes;
	public static String unspscCodes;
	public static String altCompanyNames;
	public static String yearAndUSSales;
	public static String salesCountries;
	public static String invoiceCurrencies;
	public static String billingIDs;
	public static String taxIDNumbers;
	public static String buSicCodes;
	public static String buCptNodeMappings;
	public static String buExternalRef;
	public static String companyOwnership;
	public static String companyUrl;
	public static String description;
	public static String dunsNumber;
	public static String ediCapable;
	public static String dunsValue;
	public static String globalCompanyId;
	public static String globalLocationNumber;
	public static String globalPlatformId;
	public static String accessType;
	public static String globalVisibility;
	public static String govClassification;
	public static String hierarchySupportedFlag;
	public static String industryType;
	public static String internalName;
	public static String isoQualified;
	public static String isoQualifiedDate;
	public static String isoQualifyingAgency;
	public static String miscInfo;
	public static String numberEmployees;
	public static String numberPlants;
	public static String partyId;
	public static String paymentMethod;
	public static String preferredLanguageCode;
	public static String qs9000Qualified;
	public static String qs9000QualifiedDate;
	public static String qs9000QualifyingAgency;
	public static String taxExemptId;
	public static String taxExemptStatus;
	public static String uccEanId;
	public static String upgradeStatus;
	public static String yearEstablished;
	public static String billableDirectlyByGXS;
	public static String privateBu;
	public static String testBu;
	public static String parentBuId;
	public static String communityId;
	public static String participationType;
	public static String system;
	public static String typeName;
	public static String typeId;
	public static String addressType;
	public static String addressBuId;
	public static String addressPostalAddress;
	public static String addressPostalAddress1;
	public static String addressPhoneNumber;
	public static String addressFaxNumber;
	public static String addressState;
	public static String addressPostalCode;
	public static String creationDate;
	public static String modifiedDate;
	public static String uniqueId;
	public static String addressFederativeUnitCode;
	public static String addressMunicipalityCode;
	public static String entityType;
	
		
	
	@BeforeMethod
	public static void Environment() throws IOException {
		Properties prop_Node=new Properties();		
		FileInputStream File_Node= new FileInputStream(BaseDir+"Env.properties");
		try {
			prop_Node.load(File_Node);
			BaseURI= prop_Node.getProperty("BaseURI");
			im_bu_id=prop_Node.getProperty("Header_im_bu_id");
			im_user_id=prop_Node.getProperty("Header_im_user_id");
			im_service_instance_id=prop_Node.getProperty("Header_im_service_instance_id");
			im_community_id=prop_Node.getProperty("Header_im_community_id");
			im_principal_type=prop_Node.getProperty("Header_im_principal_type");
			ContentType=prop_Node.getProperty("Header_ContentType");
			DBRetryCount=prop_Node.getProperty("DBRetryCount");
			DBQuerySleepTime=prop_Node.getProperty("DBQuerySleepTime");
					
			
		} catch (IOException e) {
			
			System.out.println(BaseDir+"Env.properties"+" file not found");
			
		}

	}
	
	
	
	@Test
	public void BUCreate_Lens() throws ClassNotFoundException, InterruptedException, IOException  {
		
		SoftAssert softAssertion= new SoftAssert();
		
		Datatable dt= new Datatable(BaseDir+"CMD_TestData.xlsx", "Sheet1");
		
		
		List<Map<String, String>> TDMap = dt.getColumnAndRowsAsMap();
		
		
		{
			for (int i = 0 ; i < TDMap.size() ; i++) {
				Map<String, String> myMap = TDMap.get(i);     
									
				//** Getting values from Excel
				RUNNABLE = myMap.get("RUNNABLE").toString();
				
				
				//** checking whether scenario to be executed 
				if (RUNNABLE.equalsIgnoreCase("YES")){
					
				
				myMap.remove("RUNNABLE");
				
				TestCaseId = myMap.get("TestCaseId").toString();
				myMap.remove("TestCaseId");
				
				Scenario = myMap.get("Scenario").toString();					
				System.out.println("The scenario for BUCreate is " + Scenario);
				myMap.remove("Scenario");
				
				ScenarioType = myMap.get("ScenarioType").toString();					
				myMap.remove("ScenarioType");
				
				//*Getting values for Request body from xls
				name = myMap.get("name").toString();
				addressCounty = myMap.get("addressCounty").toString();
				addressCity = myMap.get("addressCity").toString();
				addressCountryCode = myMap.get("addressCountryCode").toString();					
				status = myMap.get("status").toString();
				currentLocale = myMap.get("currentLocale").toString();
				naicsCodes = myMap.get("naicsCodes").toString();
				unspscCodes = myMap.get("unspscCodes").toString();
				altCompanyNames = myMap.get("altCompanyNames").toString();
				yearAndUSSales = myMap.get("yearAndUSSales").toString();
				salesCountries = myMap.get("salesCountries").toString();
				invoiceCurrencies = myMap.get("invoiceCurrencies").toString();
				billingIDs = myMap.get("billingIDs").toString();
				taxIDNumbers = myMap.get("taxIDNumbers").toString();
				buSicCodes = myMap.get("buSicCodes").toString();
				buCptNodeMappings = myMap.get("buCptNodeMappings").toString();
				buExternalRef = myMap.get("buExternalRef").toString();
				companyOwnership = myMap.get("companyOwnership").toString();
				companyUrl = myMap.get("companyUrl").toString();
				description = myMap.get("description").toString();
				dunsNumber = myMap.get("dunsNumber").toString();
				ediCapable = myMap.get("ediCapable").toString();
				dunsValue = myMap.get("dunsValue").toString();
				globalCompanyId = myMap.get("globalCompanyId").toString();
				globalLocationNumber = myMap.get("globalLocationNumber").toString();
				globalPlatformId = myMap.get("globalPlatformId").toString();
				accessType = myMap.get("accessType").toString();
				globalVisibility = myMap.get("globalVisibility").toString();
				govClassification = myMap.get("govClassification").toString();
				hierarchySupportedFlag = myMap.get("hierarchySupportedFlag").toString();
				industryType = myMap.get("industryType").toString();
				internalName = myMap.get("internalName").toString();
				isoQualified = myMap.get("isoQualified").toString();
				isoQualifiedDate = myMap.get("isoQualifiedDate").toString();
				isoQualifyingAgency=myMap.get("isoQualifyingAgency").toString();
				miscInfo=myMap.get("miscInfo").toString();
				numberEmployees=myMap.get("numberEmployees").toString();
				numberPlants=myMap.get("numberPlants").toString();
				partyId=myMap.get("partyId").toString();
				paymentMethod=myMap.get("paymentMethod").toString();
				preferredLanguageCode=myMap.get("preferredLanguageCode").toString();
				qs9000Qualified=myMap.get("qs9000Qualified").toString();
				qs9000QualifiedDate=myMap.get("qs9000QualifiedDate").toString();
				qs9000QualifyingAgency=myMap.get("qs9000QualifyingAgency").toString();
				taxExemptId=myMap.get("taxExemptId").toString();
				taxExemptStatus=myMap.get("taxExemptStatus").toString();
				uccEanId=myMap.get("uccEanId").toString();
				upgradeStatus=myMap.get("upgradeStatus").toString();
				yearEstablished=myMap.get("yearEstablished").toString();
				billableDirectlyByGXS=myMap.get("billableDirectlyByGXS").toString();
				privateBu=myMap.get("privateBu").toString();
				testBu=myMap.get("testBu").toString();
				parentBuId=myMap.get("parentBuId").toString();
				communityId=myMap.get("communityId").toString();
				participationType=myMap.get("participationType").toString();
				system=myMap.get("system").toString();
				typeName = myMap.get("typeName").toString();
				typeId = myMap.get("typeId").toString();
				addressType = myMap.get("addressType").toString();
				addressBuId = myMap.get("addressBuId").toString();
				addressPostalAddress = myMap.get("addressPostalAddress").toString();
				addressPostalAddress1 = myMap.get("addressPostalAddress1").toString();
				addressFaxNumber = myMap.get("addressFaxNumber").toString();
				addressState = myMap.get("addressState").toString();
				addressPostalCode = myMap.get("addressPostalCode").toString();
				creationDate = myMap.get("creationDate").toString();
				modifiedDate = myMap.get("modifiedDate").toString();
				uniqueId = myMap.get("uniqueId").toString();
				addressFederativeUnitCode = myMap.get("addressFederativeUnitCode").toString();
				addressMunicipalityCode = myMap.get("addressMunicipalityCode").toString();
				entityType = myMap.get("entityType").toString();
				
						
				//** Calling CMD post method for BU create
				Response abc= CMD_POST.CMD_POST(myMap ,BaseURI ,im_bu_id ,im_user_id ,im_service_instance_id ,im_community_id ,im_principal_type ,ContentType);
				
				
							
					int status_code=abc.getStatusCode();
					System.out.println("CMD POST response for BU CREATE " + status_code);
					
					Assert.assertEquals(201 , status_code);
					softAssertion.assertEquals(201 , status_code);
					
					
						
					String location= abc.getHeader("Location");
					
					System.out.println("Location Header - " + location  );
					
						
					//** getting the buid from the response 
					String cmdLocationParts[]= location.split("/");
					
					for (int j =0 ; j<cmdLocationParts.length; j++) {
						
														
						if (j+1==cmdLocationParts.length) {
							
							String buid=cmdLocationParts[j];
							
						String lens_buid="TG16-DEV-"+buid;
						
						System.out.println("the lens required buid is " +  lens_buid);	
						
						logger.log(Level.INFO,"the lens required buid " +  lens_buid);
						//logger.log(Level.WARNING,"TEST WARNING ");
						
						
						//**Querying Lens DB
						String sqlQuery="select name from public.organization where orgid = '"+ lens_buid+ "'";
						
						sqlQuery=sqlQuery.trim();
						
						System.out.println("sqlquery is  "+sqlQuery);
						
										
						Postgres_Connection_lens Postgres_con=new Postgres_Connection_lens();
						List<String> queryresult = null;
						
						try {
							
							queryresult = Postgres_con.getDataFromDataBase(sqlQuery, "name");
													
											
							if (queryresult.size()==0) {
								int m=0;
								
								//**Converting retrycount from string to int
								int c=Integer.parseInt(DBRetryCount);
								int sTime=Integer.parseInt(DBQuerySleepTime);
								
								innerLoop:
								
								for (m=0;m<=c;m++) {
									System.out.println("waiting for 10secs and retry query");
									Thread.sleep(sTime);
									queryresult = Postgres_con.getDataFromDataBase(sqlQuery, "name");
									
									//System.out.println("Value of m " + m);
									
									if(queryresult.size()>0) {
										String queryres0=queryresult.get(0);
										queryres0=queryres0.trim();
										System.out.println("Company name in the Lens DB " + queryres0);
										
										/*
										ExtentTest test;
										test=report.startTest("BUID updated on Lens DB", "Company name updated on Lens DB");
										*/
										
										if (queryres0==name) {
											System.out.println("Lens DB successfully updated with the CMD data");
										}
										
										break innerLoop;
									}else if (m==c) {
										
										//Assert.fail("BUID not updated on Lens DB");
										
										logger.log(Level.SEVERE," BUID not updated on Lens DB");
										
										logger.log(Level.INFO,"BUID not updated on Lens DB for company - " +  name + " BUID - "+ buid);
										//logger.log(Level.INFO,"BUID not updated on Lens DB for BUID " +  buid);
										//logger.log(Level.SEVERE,"for Companyname "+ name);
										//logger.log(Level.SEVERE, "for BUID" + buid );
																	
										softAssertion.fail("Buid not updated in lens");
										//break innerLoop;
										continue;
									}
									
									
								}
								
								System.out.println("outside loop check ");
								
							}
							
							
						} catch (ClassNotFoundException e) {
							
							e.printStackTrace();
						}
						
						
						System.out.println("org name  "+ queryresult);
						
						
							
						}
						
						
					}
					
										
																						
				}
		
		
		
		
	
		
		
	}
	
	}
		
	}		


	

}
