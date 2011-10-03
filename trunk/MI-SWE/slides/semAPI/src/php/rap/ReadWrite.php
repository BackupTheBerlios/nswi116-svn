<?php
  define("RDFAPI_INCLUDE_DIR", "rdfapi-php/api/");
  include(RDFAPI_INCLUDE_DIR . "RdfAPI.php");
  
  // Create a new MemModel
  $model = ModelFactory::getDefaultModel();
  
  // Load and parse document, posibilities: rdf, n3, nt
  $model->load("../../resources/hello.nt", "nt");
  
  // Write to outpupt
  // Also possible: $model->writeAsHtml();
  $model->writeAsHtmlTable();
  
  // Save to file, posibilities: rdf, n3, nt
  $model->saveAs("../../resources/hello.n3", "n3");
?>
