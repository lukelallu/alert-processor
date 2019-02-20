Details of the look up service for wire reporting [ this will be a generic service, details for from POC (SpringBoot JSON rest service) done]

 

Summary

1.      Lookup service design details

2.      Entitlement Look Up Request

3.      Entitlement Response

4.      WADL for the service

5.      Run the service on local [only for dev integration]

 

 

Further details

1.      Look up service component and sequence diagram for interface 13[wire reporting] is attached

 

2.      Entitlement Look Up Request

http://localhost:1103/lookup/eligibility/wire?accounts=000000000001111,061000000002222,000000000003333,000000000002222 [sample, not Eligibility is picked randomly from the ENUM]

 

3.      Entitlement Response

[{"accountNo": "1230","eligibilityFlag": "NA"},{"accountNo": "2010","eligibilityFlag": "SUMMARY_ONLY"},{"accountNo": "100","eligibilityFlag": "SUMMARY_DETAILS"},{"accountNo": "450","eligibilityFlag": "EXPANDED"},{"accountNo": "450","eligibilityFlag": "EXPANDED_REMIT"}]

 

4.      WADL is attached too
http://localhost:1103/lookup/swagger-ui.html

http://localhost:1103/lookup/v2/api-docs
{"swagger": "2.0","info": {"description": "This page lists all the rest apis for Entitlement Look Up.","version": "1.0-SNAPSHOT","title": "Entitlement Rest APIs"},"host": "localhost:8080","basePath": "/","tags": [{"name": "look-up-controller","description": "eligibility Look Up"}],"paths": {"/eligibility/lookUp": {"get": {"tags": ["look-up-controller"],"summary": "get Eligibility","operationId": "lookUpEligibilityUsingGET","produces": ["application/json"],"parameters": [{"name": "accounts","in": "query","description": "accounts","required": false,"type": "array","items": {"type": "string"},"collectionFormat": "multi"}],"responses": {"200": {"description": "LookUpResponse Details Retrieved","schema": {"$ref": "#/definitions/LookUpResponse"}},"401": {"description": "Unauthorized"},"403": {"description": "Forbidden"},"404": {"description": "s=Entitlement not found"},"500": {"description": "Internal Server Error"}},"deprecated": false}}},"definitions": {"LookUpResponse": {"type": "object","properties": {"accountNo": {"type": "string","description": "accountNo could have 15 characters"},"eligibilityFlag": {"type": "string","description": "eligibilityFlag"}},"title": "LookUpResponse","description": "All details about the student. "}}}

 

5.      Run the Service [copy the jar to any folder, and run below java command] – I will upload it somewhere – it is 24 MB

###java -jar lookup.jar
mvn clean install
deploy to Application Server the lookup.war