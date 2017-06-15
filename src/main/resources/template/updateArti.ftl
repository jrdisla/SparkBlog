<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
        .row.content {height: 1500px}

        /* Set gray background color and 100% height */
        .sidenav {
            background-color: #f1f1f1;
            height: 100%;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }
            .row.content {height: auto;}
        }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-3 sidenav">
            <h4>John's Blog</h4>
            <ul class="nav nav-pills nav-stacked">
                <li><a href="/startPage/">Home</a></li>
                <li><a href="/startPage/">Home</a></li>
                <li><a href="/addUser/">Add User</a></li>
                <li><a href="/login/">Login</a></li>
                <li><a href="/invalidarSesion/">Logout</a></li>
                <li><a href="/addArticulo/">Add Article</a></li>
                <li class="active"><a href="/listArtiBy/">Modify and Delete</a></li>
            </ul><br>
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Search Blog..">
                <span class="input-group-btn">
          <button class="btn btn-default" type="button">
            <span class="glyphicon glyphicon-search"></span>
          </button>
        </span>
            </div>
        </div>

        <div class="col-sm-9">


            <form action="/individualInfo/${articulo.id?string["0"]}/" method="post" id="ac">
                <div class="input-group" margin="auto">
                    Titulo: <input name="titulo" type="text" class="form-control" value="${articulo.titulo}" placeholder="Username" aria-describedby="basic-addon1">
                </div>

                <div class="input-group" margin="auto">
                    Tags: <input name="tags" type="text" class="form-control" value="${articulo.tag}" placeholder="Separated by comma" aria-describedby="basic-addon1">
                </div>

                <div class="form-group">
                    <label for="comment">Cuerpo:</label>
                    <textarea name="comment" class="form-control" rows="5" id="ac">${articulo.cuerpo}</textarea>
                </div>

                <button type="submit" class="btn btn-primary">Actualizar</button>

            </form>

            <br><br>
            <br>
        </div>
    </div>
</div>



</body>
</html>