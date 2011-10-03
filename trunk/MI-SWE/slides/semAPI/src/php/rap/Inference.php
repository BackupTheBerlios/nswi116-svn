<?php
  
  define("RDFAPI_INCLUDE_DIR", "../rdfapi-php/api_noSVN/");
  include(RDFAPI_INCLUDE_DIR . "RdfAPI.php");

  require_once RDFAPI_INCLUDE_DIR . 'model/MemModel.php';
  require_once RDFAPI_INCLUDE_DIR . 'infModel/InfModel.php';
  require_once RDFAPI_INCLUDE_DIR . 'infModel/InfRule.php';
  require_once RDFAPI_INCLUDE_DIR . 'infModel/InfStatement.php';
  
  //create a InfmodelF
  $infModel= ModelFactory::getInfModelF('http://InfModelF.org');
  
  // Load and parse document, posibilities: rdf, n3, nt
  $infModel->load("../../resources/hello.rdf", "n3");
  
  // Print and save results
  $infModel->writeAsHtmlTable();
  $infModel->saveAs("../../resources/hello_rap_inf.xml");
?>