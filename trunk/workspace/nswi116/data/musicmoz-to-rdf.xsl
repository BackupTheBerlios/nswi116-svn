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
</rdf:RDF>
 <xsl:apply-templates/>         
</xsl:template>

	
<xsl:template match="musicmoz/style">
 <xsl:variable name="style_reformatted" select="concat('http://musicmoz.org/style/', name)"/>
 <mm:Style rdf:about="{$style_reformatted}">
  <rdfs:label><xsl:value-of select="name"/></rdfs:label>
 </mm:Style>
</xsl:template>

</xsl:stylesheet>