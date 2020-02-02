# Домашние задания по Java от MADE

# Homework 1

Программа считывает входной параметр c командной строки. Это имя файла. В этом файле находится информация о сделке в следующем формате:
```java
Trade: {
“type”: {typeValue},
“price”: {priceValue}
}
```
Возможные значения `{typeValue}: FX_SPOT, BOND, COMMODITY_SPOT, IR_SWAP` и т.д. `priceValue` – числовое значение.

Далее программа считывает информацию о сделке и создать объект, описанный в файле. Каждому типу сделки соответствует свой класс. price – поле сделки, нужно установить его значение.
Задача решалась двумя способами:
Первый способ – с помощью конструкции `switch`:
```java
switch (tradeType) {
case “FX_SPOT” : ..
```
Второй способ – через enum `TradeType` c абстрактным методом `createTrade(…)`.
Логика написана так, что ее можно было переиспользовать (например, может измениться источник данных – консоль вместо файла).

Написаня unit-тесты.

# Homework 2

Написана своя реализация HashMap на основе цепочек.

Должны поддерживаться следующие методы.
```java
public interface SimpleMap<k, v=""> {
V put(K key, V value);

V get(K key);

V remove(K key);

boolean contains(K key);

int size();

Set<k> keySet();

Collection<v> values();
}
```

Написаны unit тесты на все методы.

# Homework 3

Реализован следующий интерфейс:

```java
public interface Serializer {
    String serialize(Object o);
}
```

Интерфейс на вход принимает любой объект и переводит его в строку. Сделано две реализации, первая переводит объект в Json, вторая в Xml.

Все поля объекта сериализуются рекурсивно (кроме примитивных типов и сторк), отдельный формат сериализации поддерживается для массивов и коллекций.
При реализации можно использовать только стандартные классы jdk из пакетов java.lang и java.util. Подключать сторонние библиотеки нельзя.

# Homework 4

Реализован executor manager с подсчетом статистики и рядом других фич.
