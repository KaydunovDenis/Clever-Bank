Clever-Bank
Разработать консольное приложение для Clever-Bank.
Основные сущности:
- Банк
- Счёт
- Пользователь
- Транзакция
  Стек:
- Java 17
- Gradle
- PostgreSQL
- JDBC
- Lombok
- * Servlets
- * Docker
    !!! Spring и Hibernate использовать нельзя
    Объём данных:
1. Банков ≥ 5
2. Пользователей ≥ 20
3. Счетов ≥ 40
4. Также у пользователей могут быть счета в разных банках
   Требования:
1. Реализовать операции пополнения и снятия средств со счета
2. Реализовать возможность перевода средств другому клиенту Clever-Bank и
   клиенту другого банка. При переводе средств в другой банк использовать одну транзакцию и обеспечить безопасность. С учётом работы в многопоточной среде (избегать deadlock)
3. Регулярно, по расписанию (раз в полминуты), проверять, нужно ли начислять проценты (1% - значение подставляется из конфигурационного файла) на остаток счета в конце месяца
   ● Проверку и начисление процентов нужно реализовать асинхронно
4. Значения хранить в конфигурационном файле - .yml
5. После каждой операции необходимо сформировать чек (Приложение I)
   ● Чеки сохранять в папку check, в корне проекта
6. Применить шаблоны проектирования в разработке приложения

7. Соблюдать принципы ООП (объектно-ориентированное программирование), SOLID (принципы SOLID: единство ответственности, открытость/закрытость, подстановка Лисков, разделение интерфейсов и инверсия зависимостей), KISS (простота и интуитивность), DRY (не повторяться) и YAGNI (не добавлять ненужные функции)
8. Гарантировать, что код читаемый, поддерживаемый и содержит документацию в формате JavaDoc, соблюдается java code conventions
9. Разместить проект в любом из публичных git-репозиториев (Bitbucket, github, gitlab).
   ● Придерживаться git-flow: master - develop - feature/fix
10. Создать подробный README.md файл в корне проекта, состоящий из:
    ● Общего описания проекта
    ● Инструкция по запуску проекта
    ● CRUD операции (при их наличии) с примерами входных и выходных
    данных
11. * Обеспечить покрытие юнит-тестами на уровне более 70% (слои сервиса - 100%
      покрытие)
12. * Разработать функционал формирования выписки по транзакциям пользователя за
      месяц, год или весь период обслуживания клиента в форматах PDF и TXT
      (опционально). (Приложение II)
13. ** Реализовать операции CRUD (создание, чтение, обновление, удаление) для всех
    сущностей. (Servlet). Веб интерфейс не нужен
14. ** Рассчитать и сохранить информацию о количестве потраченных и полученных
    средств за определенный период времени, используя SQL
    ● Сохранять в папку statement-money, в корне проекта
    ● Сохранять в формате .pdf (Приложение III)
    ● Вызывать с помощью Servlet
15. ** Убедиться, что таблицы в базе данных соответствуют третьей нормальной форме (3 НФ)
16. ** Реализовать сквозное логирование в файл с использованием AspectJ для методов сервиса, логироваться должны входные аргументы и ответ сервиса
    “*” – необходимо минимум выполнить два задания со звёздочкой, больше заданий будет существенным плюсом. К этим пунктам лучше приступать после качественного решения базовых задач с применением принципов SOLID, декларативных подходов, оптимальных алгоритмов.

Приложение I Шаблон чека:
![img.png](img.png)
Приложение II Шаблон pdf для выписки:
![img_1.png](img_1.png)
Приложение III Шаблон money statement:
 ![img_2.png](img_2.png)