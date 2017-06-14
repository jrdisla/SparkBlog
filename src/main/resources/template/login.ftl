<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Práctica 3</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="panel panel-primary">
    <div class="panel-heading">${Titulo}</div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="/addUsers/">Add User</a></li>
            <li><a href="/startPage/">Articulos</a></li>

        </ul>
        <form action="/login/logged" method="post">
            <div class="input-group" margin="auto">
                Username: <input name="username" type="text" class="form-control" placeholder="Username" aria-describedby="basic-addon1">
            </div>
            <div class="input-group">
                Password: <input name="password" type="text" class="form-control" placeholder="Your Password" aria-describedby="basic-addon1">
            </div>
            <p>

            </p>
            <button type="submit" class="btn btn-primary">Login</button>

        </form>
    </div>
</div>


</body>
</html>