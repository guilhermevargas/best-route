# best-route
#Getting Started

###Como Executar:
Necessário maven e java 8 instalado

Run app

cd best-route

mvn spring-boot:run -Dspring-boot.run.arguments="/path/file-name.csv"


Run unit tests

cd best-route

mvn clean install

###Estrutura de arquivos e pacotes:

main:
br/com/bexs/bestroute/api - endpoints para consulta e criação de rotas

br/com/bexs/bestroute/dto - data transfer object, utilizado para entrada de dados e sáida da api

br/com/bexs/bestroute/exception - handler de exceptions e exceptions

br/com/bexs/bestroute/model - classes de domínio

br/com/bexs/bestroute/repository - camada de persistencia

br/com/bexs/bestroute/service - regra de negócido

For further reference, please consider the following sections:

test:

br/com/bexs/bestroute/api - testes integrados, sobe a aplicação e testa a requisição pros endpoints

br/com/bexs/bestroute/repository - teste unitário da camada de persistencia

br/com/bexs/bestroute/service - teste unitário da camada de negócio

###Decisões de design adotadas para a solução:

Para estruturar o dado foi utilizado o conceito de grafo com matriz adjacente, pois as rotas que podem
ser percorridos são representadas pelo pares de vertices.

Para calcular a melhor rota com preço mais baixo, foi utilizado o algoritimo Floyd-Warshal.
Algoritimo O(Vˆ3), compara todos os pares de vertices utilizando conceito de programação dinâmica.

Toda vez que é solicitada a busca pela melhor rota o calculo é refeito, porém com mais informações de
volume de transação e armazenamento, poderiamos definir estratégias de chache e renova-lo
apenas quando novas rotas fossem inseridas.

###Descreva sua APÌ Rest de forma simplificada.

/routes
  
  POST: CRIA NOVA ROTA
  
  curl --location --request POST 'localhost:8080/routes' \
  --header 'Content-Type: application/json' \
  --data-raw '{
     "from":"ZZZ",
     "to":"AAA",
     "cost":33
  }'

  GET: MELHOR ROTA
  
  curl --location --request GET 'localhost:8080/routes?from=GRU&to=CDG'






