<html>
<body>
<form method="get">
<input type="text" name="musicstyle" size="25" value="<%= request.getParameter("musicstyle")%>">
<input type="submit" value="Submit">
</form>
<H1><%= request.getParameter("musicstyle")%></H1>
</body>
</html>