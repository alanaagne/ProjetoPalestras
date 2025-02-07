# 🎓 Projeto de Gestão de Palestras

Este projeto foi desenvolvido como parte da disciplina de Linguagem de Programação II. Ele tem como objetivo gerenciar eventos, palestras e inscrições de ouvintes.

## 🌟 Funcionalidades

- **Cadastro de Palestras:** Permite o cadastro de novas palestras associadas a um evento.
- **Inscrição de Ouvintes:** Permite a inscrição de ouvintes em palestras existentes.
- **Alteração de Palestras:** Permite alterar o dia, horário e local de palestras, verificando e ajustando a agenda para evitar conflitos.
- **Listagem de Palestras:** Lista todas as palestras por dia e horário.

## 🏗️ Arquitetura

A arquitetura do projeto é baseada na divisão de responsabilidades entre diferentes classes:
- **`Evento.java`**: Representa um evento que pode ter várias palestras.
- **`Palestra.java`**: Representa uma palestra específica.
- **`Ouvinte.java`**: Representa um ouvinte inscrito em uma palestra.
- **`EventoDAO.java`**: Responsável pelo armazenamento e carregamento dos dados de eventos, palestras e ouvintes.
- **`Main.java`**: Classe principal que gerencia a interação com o usuário.

## 🗂️ Estrutura de Pastas


```
ProjetoPalestras/
├── src/
│   └── main/
│       └── java/
│           └── br/
│               └── com/
│                   └── meuProjeto/
│                       ├── app/
│                       │   └── Main.java
│                       ├── modelo/
│                       │   ├── Evento.java
│                       │   ├── Palestra.java
│                       │   └── Ouvinte.java
│                       └── persistencia/
│                           └── EventoDAO.java
├── data/
│   └── eventos.txt
└── README.md

```


## 🚀 Como Usar

1. **Clonar o Repositório**
   ```sh
   git clone https://github.com/alanaagne/ProjetoPalestras.git
   cd ProjetoPalestras

2. **Compilar o Projeto**
   ```sh
   javac -d bin br/com/meuProjeto/**/*.java

3. **Executar o Projeto**
   ```sh
   java -cp bin br.com.meuProjeto.app.Main

4. **Interagir com o Sistema**

- Escolha um evento.

- Cadastre novas palestras, inscreva-se em palestras existentes ou altere palestras.

- Os dados serão armazenados no arquivo eventos.txt.

## 📂 Exemplo de Conteúdo dos Arquivos de Dados

Evento 1

Título da Palestra 1;Palestrante 1;2023-05-01T10:00;Sala 1;50;10

Ouvinte 1;ouvinte1@example.com

Ouvinte 2;ouvinte2@example.com

---
Título da Palestra 2;Palestrante 2;2023-05-02T14:00;Sala 2;30;20

Ouvinte 3;ouvinte3@example.com

Ouvinte 4;ouvinte4@example.com

---
Evento 2

Título da Palestra 3;Palestrante 3;2023-06-01T10:00;Sala 3;100;50

Ouvinte 5;ouvinte5@example.com

Ouvinte 6;ouvinte6@example.com

---
* ⚠️ Sendo os últimos números a capacidade máxima de ouvintes e quantos já estão cadastrados.


## 🎉 Conclusão

**Este projeto proporciona uma forma eficiente de gerenciar eventos e palestras, permitindo o cadastro, inscrição e modificação das palestras e ouvintes, garantindo uma gestão organizada e sem conflitos de agenda.**


