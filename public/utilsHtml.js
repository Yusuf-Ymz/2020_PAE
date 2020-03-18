
function printTable(containerElementId, arrayToPrint, thTab) {

    $("#carouselExampleIndicators").hide();
    $("#users_preinscrit_component").hide();
    console.log(arrayToPrint);
    let div_container = document.getElementById(containerElementId);
    div_container.innerHTML = "";
    $("#" + containerElementId).show();
    let table = document.createElement("table");
    table.className = "table";
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
        const element = arrayToPrint[x];
        for (const propriete in element) {
            let monChamp = document.createElement("td");
            if (propriete === "photoPreferee") {
                if(element[propriete] == null){
                    monChamp.innerHTML = "<img class='image' src='./images/1.jpg'/>";
                }else{
                    monChamp.innerHTML = "<img class='image' src='"+ element[propriete] + "'/>";
                }
            } else {
                monChamp.innerHTML = element[propriete];
            }
            trData.appendChild(monChamp);
        }
    }
}

export default printTable