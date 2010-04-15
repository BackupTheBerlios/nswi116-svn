<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:mm="http://musicmoz.org/"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"	
	version="1.0">

<xsl:template match="/">
<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
         xmlns:dc="http://purl.org/dc/elements/1.1/"
         xmlns:ex="http://example.org/stuff/1.0/">
 <xsl:apply-templates select="/search/events"/>         
</rdf:RDF>
</xsl:template>


<xsl:template match="events/event">
 <xsl:variable name="event_uri" select="@id"/>
 <id id="{$event_uri}" />
</xsl:template>	

</xsl:stylesheet>