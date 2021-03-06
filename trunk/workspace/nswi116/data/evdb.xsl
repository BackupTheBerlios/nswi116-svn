<?xml version="1.0" encoding="utf-8" ?>
<!-- souhrnna transformace
event
when
where
geopt
-->
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:mm="http://musicmoz.org/"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:gd="http://maps.google.com/"
	xmlns:evdb="http://eventful.com/"
	version="1.0">

  <xsl:template match="/">
    <rdf:RDF
    	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    	xmlns:gd="http://maps.google.com/"
		xmlns:evdb="http://eventuful.com/">
      <xsl:apply-templates select="search/events" />
    </rdf:RDF>
  </xsl:template>

  <xsl:template match="search/events/event">
    <evdb:Event rdf:about="http://eventful.com/events/{@id}">
      <rdfs:label><xsl:value-of select="title" /></rdfs:label>
      <evdb:hasWhen rdf:resource="http://eventful.com/events/{@id}_When"/>
	  <evdb:hasWhere rdf:resource="http://eventful.com/events/{@id}_Where"/>
    </evdb:Event>
	<rdf:Description rdf:about="http://eventful.com/events/{@id}_When">
      <gd:endTime><xsl:value-of select="stop_time" /></gd:endTime>
      <gd:startTime><xsl:value-of select="start_time" /></gd:startTime>
    </rdf:Description>
	<rdf:Description rdf:about="http://eventful.com/events/{@id}_Where">
      <gd:postalAddress><xsl:value-of select="venue_address" />, <xsl:value-of select="city_name" />, <xsl:value-of select="country_abbr" /></gd:postalAddress>
	  <rdfs:label><xsl:value-of select="venue_name" /></rdfs:label>
      <gd:hasGeoPt rdf:resource="http://eventful.com/events/{@id}_GeoPt"/>
    </rdf:Description>
	<rdf:Description rdf:about="http://eventful.com/events/{@id}_GeoPt">
		<gd:lat><xsl:value-of select="latitude"/></gd:lat>
		<gd:lon><xsl:value-of select="longitude"/></gd:lon>
	</rdf:Description>

  </xsl:template>
</xsl:stylesheet>