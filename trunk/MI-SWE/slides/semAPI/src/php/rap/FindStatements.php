<?php
  define("RDFAPI_INCLUDE_DIR", "../rdfapi-php/api_noSVN/");
  include(RDFAPI_INCLUDE_DIR . "RdfAPI.php");
  
  require_once RDFAPI_INCLUDE_DIR . 'model/MemModel.php';
  require_once RDFAPI_INCLUDE_DIR . 'infModel/InfModel.php';
  require_once RDFAPI_INCLUDE_DIR . 'infModel/InfRule.php';
  require_once RDFAPI_INCLUDE_DIR . 'infModel/InfStatement.php';
  

  $infModel= ModelFactory::getInfModelF('http://InfModelF.org');
  $infModel->load("../../resources/hello.rdf", "n3");
  
  //create parameters for find (swe:Human and rdf:type)
  $swe_human = new Resource("http://www.fit.cvut.cz/subjects/mi-swe#Human");
  $rdf_type = new Resource("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

  //find all stements (subject: arbitrary, predicate: rdf:type object: swe:Human)
  $result_model = $infModel->find(NULL, $rdf_type, $swe_human);

  //iterate the result statements
  $it = $result_model->getStatementIterator();   
  while ($it->hasNext()) {
        $statement = $it->next();
        //print subject URI
        echo $statement->getLabelSubject() ."\n<br>\n";
  }   
?>

