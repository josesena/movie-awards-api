# 🎬 Golden Raspberry Awards API

---

## 📌 Objetivo

Processar os dados de um arquivo CSV contendo os indicados ao prêmio, identificar os produtores vencedores e retornar:

- Quem teve o **maior intervalo** entre dois prêmios consecutivos.
- Quem teve o **menor intervalo** entre dois prêmios consecutivos.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (em memória)
- MapStruct
- OpenCSV
- Lombok
- JUnit 5 (Jupiter)
- MockMvc

## Como executar
```bash
./mvnw spring-boot:run
```
Acesse: `http://localhost:8080/awards/intervals`

## Testes de integração
```bash
./mvnw test
```

