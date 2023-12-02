package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 {

  // убран неиспользуемый count

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    return persons.stream().map(Person::getFirstName).skip(1).toList();
    // убрал проверку на пустоту списка - в любом случае stream вернет пустой список, даже если изначально входящий был пуст.
    // первую персону пропускаем, а не ремувим (реализуем идемпотентность)
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
    // stream, а тем более использование distinct, заменено на создание HashSet -
    // структура уже хранит уникальные элементы и нет необходимости в доп операциях,
    // элементы достаточно переложить из List в новый Set
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull).collect(Collectors.joining(" "));
    // код стал короче и нагляднее. исправлено задвоение SecondName,
    // джоин производится в стримах с фильтрацией null-значений
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, this::convertPersonToString, (person1, person2) -> person1));
    // от цикла ушел к стриму для повышения читаемости кода
    // исключена ситуация, когда secondName пустой и итоговая строка начнется с пробела
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> personSet = new HashSet<>(persons1);
    personSet.retainAll(persons2);
    return !personSet.isEmpty();
    // retain'ом оставляем только повторяющиеся элементы: если в итоге список пустой, значит повторений нет
    // по сложности снизились с O(n*m) до O(n+m)
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
    // нет необходимости в создании переменной для счетчика
    // и уж тем более вычислять итоговое количество перебором получившейся коллекции
  }
}
