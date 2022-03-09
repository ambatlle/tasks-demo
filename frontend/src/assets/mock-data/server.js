const jsonServer = require('json-server')
const server = jsonServer.create()
const router = jsonServer.router('./src/assets/mock-data/tasks.json')
const middlewares = jsonServer.defaults()

server.use(function(req, res, next){
  setTimeout(next, 500);
});

// Set default middlewares (logger, static, cors and no-cache)
server.use(middlewares)

// Add custom routes before JSON Server router
server.get('/echo', (req, res) => {
  res.jsonp(req.query)
})

// To handle POST, PUT and PATCH you need to use a body-parser
// You can use the one used by JSON Server
server.use(jsonServer.bodyParser)
server.use((req, res, next) => {
  if (req.method === 'POST' || req.method === 'PATCH') {
    console.info(`body ${req.method}`, req.body);
  }
  // Continue to JSON Server router
  next()
})

// Use default router
server.use(router)
server.listen(8080, () => {
  console.log('JSON Server is running')
})
