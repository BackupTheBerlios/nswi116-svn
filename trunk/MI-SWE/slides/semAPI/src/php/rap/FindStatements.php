<?php
  define("RDFAPI_INCLUDE_DIR", "rdfapi-php/api/");
  include(RDFAPI_INCLUDE_DIR . "RdfAPI.php");  
  $infModel= ModelFactory::getInfModelF('http://InfModelF.org');
  $infModel->load("../../resources/hello.nt", "nt");
  
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
        echo $statement->getLabelSubject() ."\n";
  }   
?>

