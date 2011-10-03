<?php
  define("RDFAPI_INCLUDE_DIR", "rdfapi-php/api/");
  include(RDFAPI_INCLUDE_DIR . "RdfAPI.php");
  
  //create a InfmodelF
  $infModel= ModelFactory::getInfModelF('http://InfModelF.org');
  
  // Load and parse document, posibilities: rdf, n3, nt
  $infModel->load("../../resources/hello.nt", "nt");
  
  // Print and save results
  $infModel->writeAsHtmlTable();
  $infModel->saveAs("../../resources/hello_rap_inf.xml");
?>
