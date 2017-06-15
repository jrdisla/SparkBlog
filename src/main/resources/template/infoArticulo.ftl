<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Práctica 2</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>


<div class="panel panel-default">
    <div class="panel-heading">${Titulo}</div>
    <div class="panel-body"><ul class="list-group">
        <li class="list-group-item">Titulo: ${articulo.titulo}</li>
        <li class="list-group-item">Tags: ${articulo.tag}</li>
        <div class="form-group">
            <label for="comment">Cuerpo:</label>
            <textarea name="comment" class="form-control" rows="5" id="ac">${articulo.cuerpo}</textarea>
        </div>
    </ul>
        <p>

        </p>

        <a href="/startPage/" class="btn btn-primary"  role="button">Start Page</a>
    </div>


</body>
</html>