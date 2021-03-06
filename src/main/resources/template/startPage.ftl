<!DOCTYPE html>
<html lang="en">
<head>
    <title>Practica 3</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .row.content {height: 1500px}

        .sidenav {
            background-color: #f1f1f1;
            height: 100%;
        }

        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

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
                        <h4>Spark Blog</h4>
                        <ul class="nav nav-pills nav-stacked">
                            <li class="active"><a href="/startPage/">Home</a></li>
                            <li><a href="/addUser/">Add User</a></li>
                            <li><a href="/login/">Login</a></li>
                            <li><a href="/invalidarSesion/">Logout</a></li>
                            <li><a href="/addArticulo/">Add Article</a></li>
                            <li><a href="/listArtiBy/">Modify and Delete</a></li>
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


                    ${code}

                        <br><br>
                        <br>
                    </div>
                </div>
            </div>
</body>
</html>