# 📘 Darbo laiko ir statybos objektų valdymo sistema

---

## 📖 Projekto aprašymas

Šis projektas – tai internetinė darbo laiko ir statybos objektų valdymo sistema, skirta darbuotojų veiklos apskaitai, objektų administravimui bei ataskaitų generavimui.

Sistema leidžia skirtingų tipų vartotojams (administratoriams, vadovams ir darbuotojams) naudotis funkcijomis pagal jų roles. Darbuotojai registruoja savo darbo laiką, vadovai prižiūri objektus ir analizuoja darbuotojų veiklą, o administratoriai valdo visą sistemą.

Papildomai sistema palaiko mėnesinių ataskaitų generavimą bei „mėnesio uždarymo“ mechanizmą(optional), kuris užtikrina duomenų vientisumą ir realią buhalterinę logiką.

---

## 🎯 Projekto tikslai

- Įgyvendinti vartotojų valdymą su rolėmis (ADMIN, MANAGER, WORKER)
- Valdyti statybos objektus (kūrimas, redagavimas, statusai)
- Registruoti darbuotojų kasdienį darbo laiką (TimeEntry)
- Užtikrinti darbuotojų priskyrimą objektams (Many-to-Many ryšys)
- Įgyvendinti prieigos kontrolę pagal roles (RBAC)
- Generuoti darbo ataskaitas (REPORTS)
- Įgyvendinti mėnesio uždarymo (MONTHLY CLOSING) funkcionalumą (optional)

---

## 🧱 Sistemos architektūra

Sistema kuriama naudojant sluoksninę architektūrą:

- **Controller sluoksnis** – REST API užklausų valdymas  
- **Service sluoksnis** – verslo logika  
- **Repository sluoksnis** – duomenų bazės operacijos  
- **Model sluoksnis** – duomenų struktūros ir ryšiai  
- **Security sluoksnis** – JWT autentifikacija ir autorizacija  

---

## 🗂️ Pagrindinės esybės ir ryšiai

### 👤 1. Vartotojas (User)

Atvaizduoja sistemos vartotoją.

**Laukai:**
- vardas  
- el. paštas  
- slaptažodis (hash)  
- rolė (ADMIN, MANAGER, WORKER)  

**Ryšiai:**
- Vienas vartotojas gali turėti daug TimeEntry  
- Vadovas gali valdyti kelis objektus  
- Darbuotojas gali būti priskirtas keliems objektams  

---

### 🏗️ 2. Statybos objektas (ConstructionSite / Project)

Atvaizduoja darbo objektą.

**Laukai:**
- pavadinimas  
- aprašymas  
- statusas (PLANNED, ACTIVE, FINISHED, CLOSED)  
- pradžios data  
- pabaigos data  
- prioritetas (LOW, MEDIUM, HIGH)  
- priskirtas vadovas (User)  

**Ryšiai:**
- Vienas objektas turi vieną vadovą (MANAGER)  
- Vienas objektas turi daug darbuotojų  
- Vienas objektas turi daug TimeEntry  

---

### ⏱️ 3. Darbo įrašas (TimeEntry)

Atvaizduoja kasdienį darbuotojo darbą.

**Laukai:**
- data  
- darbo valandos  
- viršvalandžiai  
- atstumas  
- statusas (WORKING, VACATION, SICK)  
- locked (ar užrakintas mėnesio uždarymo metu) (optional)

**Ryšiai:**
- Priklauso vienam User  
- Priklauso vienam ConstructionSite  

---

### 🔗 4. Darbuotojų priskyrimas objektams (Many-to-Many)

Apibrėžia, kuriuose objektuose darbuotojas gali dirbti.

- darbuotojas gali turėti daug objektų  
- objektas gali turėti daug darbuotojų  

---

### 📊 5. Mėnesio uždarymas (MonthlyClosure) (optional)

Valdo periodų uždarymą.

**Laukai:**
- metai  
- mėnuo  
- statusas (OPEN / CLOSED)  
- uždarė (User)  
- uždarymo data  

**Funkcija:**
- užrakina visus TimeEntry tam mėnesiui  

---

### 📈 6. Ataskaitos (Reports)

Sistema generuoja:

- darbuotojo mėnesio darbo ataskaitą  
- objekto darbo ataskaitą  
- TOP darbuotojų sąrašą  
- viršvalandžių statistiką  

---

## 🔐 Rolės ir teisės

### 👑 Administratorius
- vartotojų CRUD  
- objektų CRUD  
- visų TimeEntry peržiūra ir redagavimas  
- mėnesio uždarymas  

---

### 🧑‍💼 Vadovas (Manager)
- savo objektų valdymas
- objektų CRUD   
- objektų TimeEntry peržiūra  
- savo TimeEntry kūrimas  

---

### 👷 Darbuotojas (Worker)
- peržiūri priskirtus objektus  
- kuria savo TimeEntry  
- redaguoja savo TimeEntry (jei mėnuo neuždarytas)  

---

## ⚙️ Verslo taisyklės

- Darbuotojas gali dirbti tik priskirtuose objektuose  
- TimeEntry negali būti redaguojami uždarytame mėnesyje (optional) 
- Vadovas mato tik savo objektų duomenis  
- Kiekvienas objektas turi vieną vadovą  
- Vienas vartotojas gali turėti kelis TimeEntry per dieną  
- Mėnesio uždarymas užrakina visus to periodo įrašus (optional) 

---

## 📅 Monthly Closing logika (Optional)

- mėnesio pabaigoje ADMIN/MANAGER uždaro mėnesį  
- sistema užrakina TimeEntry  
- duomenys tampa read-only  
- galima generuoti galutines ataskaitas  

---

## 📊 REPORTS funkcionalumas

### 📌 Darbuotojo ataskaita
- bendros valandos per mėnesį  
- viršvalandžiai  
- darbo dienos  

### 📌 Objekto ataskaita
- kiek valandų dirbta objekte  
- aktyviausi darbuotojai  

### 📌 TOP darbuotojai
- daugiausiai valandų  
- daugiausiai viršvalandžių  

---

## 🧪 Duomenų bazė

Naudojama **H2 in-memory duomenų bazė**

### Privalumai:
- nereikia instaliacijos  
- greita  
- patogu testavimui  
- idealiai tinka mokymuisi  

---

## 🚀 Tikėtinas rezultatas

Sukurtas projektas leis:

- valdyti vartotojus ir roles  
- administruoti statybos objektus  
- sekti darbuotojų darbo laiką  
- užtikrinti prieigos kontrolę  
- generuoti ataskaitas  
- simuliuoti realią darbo apskaitos sistemą  
- įgyvendinti mėnesio uždarymo (accounting-like) logiką (optional)

## 🧪 Testai

Projekte naudojami keli testavimo tipai:

#### 🔹 Integraciniai ir API testai
Naudojami Spring Boot testai (`@SpringBootTest`, `MockMvc`, `@DataJpaTest`, `@WebMvcTest`), kurie tikrina:
- CRUD operacijas (User, Object, TimeEntry)
- REST API atsakymus ir statusus
- validacijas ir klaidų scenarijus
- roles ir prieigos kontrolę

#### 🔹 Architektūriniai (reflection) testai
Tikrina projekto struktūrą:
- ar egzistuoja būtinos klasės (Controller, Service, DTO)
- ar DTO yra `record` ir turi teisingus laukus
- ar metodai turi teisingus parametrus ir return tipus
- ar naudojamas `Pageable` ir `Page` filtravimas
- ar API grąžina DTO, o ne entity

#### 🎯 Tikslas
Užtikrinti, kad sistema veikia teisingai ir atitinka gerą architektūrą.

---

## 🔧 Galimi patobulinimai ateityje

- JWT autentifikacija (vietoj basic auth)  
- REST API dokumentacija (Swagger)  
- React frontend  
- PDF/Excel ataskaitų eksportas  
- audit log (kas ką keitė)  
- push/email pranešimai  
- PostgreSQL migracija į production

