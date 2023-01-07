# biblioteca-dona-gertrudes
Api Rest com Java(17) e Spring boot para o cadastro de clientes e funcionários de uma biblioteca, com endereço dos clientes cadastrados utilizando a Api ViaCEP.

Com essa aplicação é possível cadastrar, editar e atualizar clientes e funcionários. 
Ao cadastrar ou editar, deve ser informado um documento(CPF/CNPJ) que será validado pela Api Stella se o documento informado é um documento válido. O Cep também será validado, através da Api Via Cep, que fará a busca do endereço e logo após ele será convertido para um objeto Endereço utilizando o Gson.
Também é possível verificar quantos funcionários e clientes existem por CEP, agrupados através de uma Query personalizada e mapeada para a Jpa.
