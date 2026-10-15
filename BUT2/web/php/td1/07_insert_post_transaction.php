
<?php
include('connect_params_perso.php');
try {
    $dbh = new PDO("$driver:host=$server;dbname=$dbname", 
    $user, $pass);
    $dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $dbh->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
    echo "Connecté\n";
} catch (Exception $e) {
    die("Impossible de se connecter : " . $e->getMessage());
}

try {  
    $dbh->beginTransaction();
    $sth = $dbh->prepare("INSERT INTO forum1._document(content, author) VALUES (:content, :author) RETURNING iddoc");
    $sth->bindParam(':content', $content);
    $sth->bindParam(':author', $author);

    $content = "Bonjour les copains !";
    $author = "Alex";
    $sth->execute();
    $iddoctab = $sth->fetchAll();
    print_r($iddoctab);

    $sth2 = $dbh->prepare("insert into forum1._post(iddoc) values (:iddoc)");
    $sth2->bindParam(':iddoc', $iddoctab[0]["iddoc"]);
    $sth2->execute();

    $dbh->commit();

} catch (Exception $e) {
  $dbh->rollBack();
  echo "Failed: " . $e->getMessage();
}
?>
