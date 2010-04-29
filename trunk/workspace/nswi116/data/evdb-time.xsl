<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:mm="http://musicmoz.org/"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:gd="http://maps.google.com/"
	version="1.0">

  <xsl:template match="/">
    <rdf:RDF
    	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    	xmlns:gd="http://maps.google.com/">
      <xsl:apply-templates select="search/events" />
    </rdf:RDF>
  </xsl:template>

  <xsl:template match="search/events/event">
    <rdf:Description rdf:about="evdb:events/{@id}_When">
      <gd:endTime><xsl:value-of select="stop_time" /></gd:endTime>
      <gd:startTime><xsl:value-of select="start_time" /></gd:startTime>
    </rdf:Description>
  </xsl:template>
</xsl:stylesheet>