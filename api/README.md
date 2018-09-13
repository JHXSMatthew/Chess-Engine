# API holds on port 8080

The folder complete will be used.

## Example Usage

```
curl 127.0.0.1:8080/greeting
```

## Move API(Case Sensitive)
```
curl -H "Content-Type: application/json" -X POST -d '{"state": "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR","start": 8,"end": 22}' http://localhost:8080/move
```
Sample Output:
```
{
    "state":"xxxxxxx",
    "start": 0,
    "end" : 0
}
```
