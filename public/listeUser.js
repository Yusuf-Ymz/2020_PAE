function affichageListeUser(
    containerElementId,
    arrayToPrint,
    thTab) {

    let div_container = document.getElementById(containerElementId);
    div_container.innerHTML = "";

    let table = document.createElement("table");
    table.className = "mt-10";
    div_container.appendChild(table);

    let thead = document.createElement("thead");
    table.appendChild(thead);
    let tr = document.createElement("tr");
    thead.appendChild(tr);
    for (let i = 0; i < thTab.length; i++) {
        let th = document.createElement("th");
        th.innerHTML = thTab[i];
        tr.appendChild(th);
    }
    let tbody = document.createElement("tbody");
    table.appendChild(tbody);
    for (let x = 0; x < arrayToPrint.length; x++) {
        let trData = document.createElement("tr");
        tbody.appendChild(trData);
        const user = arrayToPrint[x];
        for (const propriete in user) {
            if (propriete === "nom" || propriete === "prenom" || propriete === "dateInscription"
                || propriete === "pseudo" || propriete === "ville") {
                let monChamp = document.createElement("td");
              

                // changer la  date soit ici soit en java
                if (propriete === "dateInscription") {
                     console.log(JSON.stringify(input));
                    var input = user[propriete];        
                    var date = new Date(JSON.stringify(input));
                    console.log(date);
                    monChamp.innerHTML = date;
                } else {

                    monChamp.innerHTML = user[propriete];
                }

                trData.appendChild(monChamp);

            }
        }


    }


}

export default affichageListeUser;
