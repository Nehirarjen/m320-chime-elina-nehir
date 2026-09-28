# Erzeugt die JavaDoc fuer Auction und AuctionHouse in Q2\docs.
# Aufruf in PowerShell: .\Q2\javadoc-erzeugen.ps1

$root = Split-Path $PSScriptRoot -Parent
$docs = Join-Path $PSScriptRoot "docs"

if (Test-Path $docs) { Remove-Item $docs -Recurse -Force }

javadoc -package -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 -quiet `
    -header '<a href="Auction.html">Auction</a> | <a href="AuctionHouse.html">AuctionHouse</a>' `
    -sourcepath "$root\D2\src" -d $docs `
    "$root\D2\src\Auction.java" "$root\D2\src\AuctionHouse.java"

# JavaDoc verlinkt den Menuepunkt "Class" nie. Wir verlinken ihn hier auf die Klassenuebersicht.
$utf8 = New-Object System.Text.UTF8Encoding($false)
Get-ChildItem $docs -Filter *.html | ForEach-Object {
    $text = [IO.File]::ReadAllText($_.FullName, $utf8)
    $neu = $text.Replace('<li>Class</li>', '<li><a href="allclasses-index.html">Class</a></li>')
    if ($neu -ne $text) { [IO.File]::WriteAllText($_.FullName, $neu, $utf8) }
}
