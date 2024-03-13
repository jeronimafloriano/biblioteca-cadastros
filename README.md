# biblioteca
Api Rest com Java(17) e Spring boot para o cadastro de clientes e funcionários de uma biblioteca, com endereço dos clientes cadastrados utilizando a Api ViaCEP.

Com essa aplicação é possível cadastrar, editar e atualizar clientes e funcionários. 
Ao cadastrar ou editar, deve ser informado um documento(CPF/CNPJ) que será validado pela Api Stella se o documento informado é um documento válido. O Cep também será validado, através da Api Via Cep, que fará a busca do endereço e logo após ele será convertido para um objeto Endereço utilizando o Gson.
Também é possível verificar quantos funcionários e clientes existem por CEP, agrupados através de uma Query personalizada e mapeada para a Jpa.

A aplicação foi configurada para utilizar o docker para executar o MySql, Prometheus e Grafana.


## Requisitos
- Java 17 ou superior
- Docker 
- Maven 2.7 ou superior

## Prometheus 
Prometheus é um sistema de monitoramento e alerta. Ele está configurado para rodar na porta http://localhost:9090/

## Grafana
Grafana irá visualizar os dados coletados do prometheus. É necessário criar um dashboard para visualizar esses dados, que pode ser importado através de um arquivo json. Aqui na raiz do projeto existe um arquivo "dash.json" que já possui um dashboard configurado e poderá ser importado para visualização no grafana.

O Grafana está configurado para rodar na porta http://localhost:3000/

## Executando a aplicação e acessando o Grafana
Na raiz do projeto, abra um terminal e execute: docker-compose up
Abra um outro terminal também na raiz do projeto e execute: mvn spring-boot:run

Nesse momento a aplicação será executada. Acesse http://localhost:3000/ com o usuário e senha "admin". No menu lateral, acesse a opção "Dashboards" e na opção "New" selecione "Import". Selecione o arquivo "dash.json" que está na raiz do projeto e clique em "Import". 
Nesse momento será apresentado o dashboard importado.



