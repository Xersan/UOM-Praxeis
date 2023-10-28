
# UOM Praxeis

### Εφαρμογή Android σε Java με σκοπό τη χρήση του [API](https://diavgeia.gov.gr/api/help "API") ανοιχτών δεδομένων της [Διαύγειας](https://diavgeia.gov.gr/ "Διαύγειας").

<br>

## Εισαγωγή
Η βασική λειτουργία αφορά την άντληση στοιχείων σχετικά με αναρτημένες πράξεις στη [Διαύγεια](https://diavgeia.gov.gr/ "Διαύγεια") από το Πανεπιστήμιο Μακεδονίας.

- Η εφαρμογή στέλνει HTTP αιτήματα στο REST API ανοιχτών δεδομένων της [Διαύγειας](https://diavgeia.gov.gr/ "Διαύγειας").
- Για την πραγματοποίηση των κλήσεων στο API χρησιμοποιείται η βιβλιοθήκη [volley](https://github.com/google/volley "volley").
- Η κύρια μορφή των μηνυμάτων που υποστηρίζει το API είναι η JSON.  

<br>

## Διευκρινίσεις

#### Συνολικά στέλνονται (ανάλογα και με τις επιλογές του χρήστη) τα ακόλουθα αιτήματα:
1. `https://diavgeia.gov.gr/opendata/organizations.json?category=UNIVERSITY`

    **Σκοπός:** Εύρεση uid (μοναδικό ID ενός οργανισμού) του Πανεπιστημίου Μακεδονίας  

2. `https://diavgeia.gov.gr/opendata/search?org=" + university + "&from_issue_date=" + year + "-01-01&to_issue_date=" + year + "-12-31`  

    **Σκοπός:** Εύρεση του συνολικού αριθμού (Total) αναρτημένων πράξεων συγκεκριμένη χρονιά\
    **Παράδειγμα μεταβλητών:** year = “2023” και university = “99206919”  

3. `https://diavgeia.gov.gr/opendata/search?org=" + university + "&status=revoked&from_date=" + year + "-01-01&to_date=" + year + "-12-31`  

    **Σκοπός:** Εύρεση του αριθμού ανακληθέντων (Revoked) πράξεων συγκεκριμένη χρονιά και προσδιορισμός αυτών που περιέχουν προσωπικά δεδομένα (PD)\
    **Παράδειγμα μεταβλητών:** year = “2023” και university = “99206919”  

4. `https://diavgeia.gov.gr/opendata/organizations/" + university + "/units.json?status=active`  

    **Σκοπός:** Εύρεση των ενεργών οργανωτικών μονάδων (Units) του Πανεπιστημίου Μακεδονίας και του αριθμού αυτών\
    **Παράδειγμα μεταβλητής:** university = “99206919”  

<br>

#### Στόχος της εφαρμογής είναι η παρουσίαση του:
1. Τρόπου κλήσης ενός API μέσω εφαρμογής Android
2. Τρόπου διαχείρισης των απαντήσεων του API σε JSON  

για αυτό και οι κλήσεις που γίνονται δεν εξαντλούν όλες τις περιπτώσεις και τις δυνατότητες του API.

<br>

## Screenshot λειτουργίας  

#### Κεντρικό μενού με το uid και τους αριθμούς των πράξεων ανά κατηγορία  

<br>

<img src="https://github.com/Xersan/UOM-Praxeis/blob/main/screenshot1.png?raw=true" alt="alt text" width="40%" height="40%" title="screenshot1">

<br>

#### Ενεργές οργανωτικές μονάδες  

<br>

<img src="https://github.com/Xersan/UOM-Praxeis/blob/main/screenshot2.png?raw=true" alt="alt text" width="40%" height="40%" title="screenshot2">

<br>
