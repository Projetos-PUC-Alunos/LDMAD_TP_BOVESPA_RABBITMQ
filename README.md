# LDMAD_TP_BOVESPA_RABBITMQ
Trabalho Prático de Laboratório de Desenvolvimento de Aplicações Móveis Distribuídas - 5º Período PUCMG - Praça da Liberdade - Engenharia de Software - Prof Rommel Carneiro


*Resumo*. _Neste presente trabalho foi desenvolvido um sistema para uma bolsa de valores qualquer, utilizando o RabbitMQ. Com o objetivo de demonstrar a ocorrência do funcionamento de um sistema que envolve transações em uma bolsa de valores qualquer, tem-se o resultado de um software. Este, é responsável por simular as transações de compra e venda em uma bolsa de valores_.

### Objetivo Geral

O objetivo geral é demonstrar como ocorre o funcionamento de um sistema que envolve transações em uma bolsa de valores qualquer.

### Tecnologias

A linguagem escolhida para o servidor foi Java. As tecnologias escolhidas para compor a construção do servidor foram WebSocket, uma biblioteca que implementa a comunicação WebSocket em Java e RabbitMQ, para a utilização do sistema de mensageria, baseado no servidor de hosteamento AMPQ Cloud.

### Observação antes de rodar a aplicação

Na classe _Planilha.java_, *TROQUE* o caminho alternativo dos arquivos para leitura e escrita de acordo com a sua máquina.


### Como rodar

1. Com o botão direito, nos arquivos (_ampq-client-5.7.1.jar, slf4j-api-1.7.26.jar, slf4-simple-1.7.26.jar_), adicione-os ao path do projeto.

2. Execute o projeto no arquivo principal *Aplicacao.java*

### Funcionamento de cada Classe

1. *Aplicacao.java*
Obtém a escolha do usuário entre compra e venda;
Obtém uma conexão com o RabbitMQ usando a classe _ServicoMensageria.java_;
Exibe uma lista de ações disponíveis e solicita quantidade e valor;
Cria uma *thread* (corretoraThread) para enviar a mensagem ao RabbitMQ usando a classe _Corretora.java_.
Também tem métodos para obter a escolha do usuário e gerar o livro de ofertas, que será salvo em um arquivo .csv.

2. *BolsaDeValores.java*
Estabelece a conexão com o RabbitMQ para consumir mensagens da bolsa de valores;
Cria uma *thread* (bolsaThread) para consumir mensagens em segundo plano;
Define métodos auxiliares para processar mensagens recebidas.

3. *Corretora.java*
Publica mensagens no RabbitMQ para representar operações de compra ou venda de ações.

4. *ServicoMensageria.java* e *RabbitMQConncetion.java*
Estabelecem a conexão com o RabbitMQ, retornando uma conexão para ser usada pelo sistema.

5. *Planilha.java*
Responsável pela manipulação dos arquivos excel (_LivroDeOfertas.csv_ e _1688438acoesbovespa.csv_).

### Funcionamento Geral

A _Aplicação.java_ será executada. É a partir dela que a interface gráfica é gerada para o cliente para interação. A classe irá obter a escolha do usuário entre compra e venda. Obtém uma conexão com o RabbitMQ usando a classe _ServicoMensageria.java_. Será exibido uma interface com as ações disponíveis, O usuário escolhe uma ação, a quantidade e o valor. Uma mensagem é enviada ao RabbitMQ representando a operação de compra ou venda. A _BolsaDeValores.java_ consome essas mensagens e processa conforme necessário.

O código usa *threads* para comunicação assíncrona com o RabbitMQ, para compra e venda, separamente. 
