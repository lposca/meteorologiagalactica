  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <style>
        body {
        font-family: Georgia, "Times New Roman", Times, serif;
        color: #555;
        word-wrap: break-word; 
        }
    </style>
  </head>
<body>
    
        <div class="container">
    
            <div class="blog-header">
            <h1 class="blog-title">Predicción climática galáctica</h1>
            <p class="lead blog-description">Dicha predicción se genera con los años relativo al planeta <b><c:out value="${planet}"></c:out></b></p>
            </div>
    
            <div class="row">
            <div class="col-lg-12 blog-main">
               <div class="blog-post">
                <h2>¿Cuántos períodos de sequía habrá?</h2>
                  <pre><code><h4>Habrá <c:out value="${prediction.drought.periods} períodos de sequia"/></h4></code></pre>
                
                <hr>
                
                <h2>¿Cuántos períodos de lluvia habrá?</h2>
                  <pre><code><h4>Habrá <c:out value="${prediction.rain.periods} períodos de lluvia"/></h4></code></pre>
                  <pre><code><h4>El día <c:out value="${prediction.rain.maxday} será el pico máximo de lluvia"/></h4></code></pre>
                <hr>
                
                <h2>¿Cuántos períodos de condiciones óptimas de presión y temperatura habrá?</h2>
                  <pre><code><h4>Habrá <c:out value="${prediction.optimalConditions.periods} períodos de condiciones óptimas de presión y temperatura"/></h4></code></pre>
                <hr>
                
                </div>
            </div>
            </div>
        </div>
    </div>


</body>
</html>