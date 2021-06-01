# Fazan

<b>Conceput de:</b> <i> Murariu Gabriela & Doboș Alexandru-Cristian </i>

<b>Descriere joc</b>
Proiectul constă în realizarea unui joc interactiv de cuvinte, cunoscut sub numele de <i>"Fazan"</i>. Jocul constă într-un schimb de cuvinte între utilizator și calculator, urmând următoarele reguli:
- Fiecare jucător trebuie să scrie un cuvânt ce va începe cu ultimele două litere ale ultimului cuvânt spus de câtre adversar;
- La inceput, jucătorul trimite calculatorului un cuvânt care începe cu o anumită literă generată random de către aplicație, urmând schimbul de cuvinte până când un jucător este inschis;
- Un jucător este închis atunci când nu există un cuvânt în baza de date care să înceapă cu ultimele două litere ale cuvântului adversarului, caz în care se începe o rundă nouă;
- Este interzisă inchiderea adversarului în primul schimb de cuvinte al fiecărei runde;
- Este interzisă folosirea unui cuvânt deja utilizat în aceeași rundă;
- Primul dintre jucători care reușește să-și închidă adversarul de cinci ori va câștiga;

<b>Tehnologii folosite</b>
- JDBC (Java Database Connectivity)
- Java Swing
- PostgreSQL

<b>Funcționalitățile aplicației</b>
- Posibilitatea introducerii unui nume de utilizator (în absența sa, numele utilizatorului este prestabilit cu <i>"Unknown player"</i>);
- Alegerea dificultății jocului (Medium sau Hard) - <i>implicit Medium</i>;
- Posibilitatea începerii unei noi runde atunci când utilizatorul nu știe să răspundă;
- Posibilitatea începerii unui nou joc atunci când a fost declarat un câștigător;

<b>Descriere aplicație</b>
- Aplicația este scrisă în limbajul de programare Java;
- Cuvintele ce pot fi folosite de către jucători sunt stocate într-o bază de date;
- Jocul este implementat pe două categorii (medium și hard). În categoria medium, calculatorul va avea un schimb normal de cuvinte cu utilizatorul. În categoria hard, calculatorul are o strategie bine conturată, oferind utilizatorului cuvinte cu cât mai puține variante de răspuns, inclusiv nicio variantă de răspuns, caz în care utilizatorul pierde runda.


<b>Contribuții membri:</b>
- Realizarea strategiei de joc <i>(Murariu Gabriela & Doboș Alexandru-Cristian);</i>
- Gestionarea bazei de date <i>(Murariu Gabriela & Doboș Alexandru-Cristian);</i>
- Logica aplicației <i>(Murariu Gabriela & Doboș Alexandru-Cristian);</i>
- Crearea interfeței aplicației <i>(Murariu Gabriela & Doboș Alexandru-Cristian);</i>
- Implementarea aplicației <i>(Murariu Gabriela & Doboș Alexandru-Cristian);</i>

