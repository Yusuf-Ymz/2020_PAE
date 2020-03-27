
function printTable(containerElementId, arrayToPrint, tabButtonValue = [], idNom, lesFonction = [], url = "") {

  $("#carouselContent").hide();

  let div_container = document.getElementById(containerElementId);
  div_container.innerHTML = "";
  $("#" + containerElementId).show();
  let table = document.createElement("table");

  table.className = "table table-bordered";
  div_container.appendChild(table);

  let thead = document.createElement("thead");
  table.appendChild(thead);

  let tr = document.createElement("tr");
  thead.appendChild(tr);

  let headers = arrayToPrint[0];
  for (const attribute in headers) {
    if (attribute !== idNom) {
      let th = document.createElement("th");
      th.innerHTML = attribute;
      tr.appendChild(th);
    }
  }

  for (let i = 0; i < tabButtonValue.length; i++) {
    let th = document.createElement("th");
    tr.appendChild(th);
  }

  let tbody = document.createElement("tbody");
  table.appendChild(tbody);
  let trData;

  for (let x = 0; x < arrayToPrint.length; x++) {

    trData = document.createElement("tr");
    tbody.appendChild(trData);
    const element = arrayToPrint[x];
    for (const propriete in element) {
      let monChamp = document.createElement("td");

      if (propriete === idNom) {
        trData.id = element[propriete];
        continue;
      }

      if (propriete === "Types d'aménagements") {
        let amenagements = "";
        element[propriete] = JSON.parse(element[propriete]);
      
        for (let i = 0; i < element[propriete].length; i++) {
          amenagements += element[propriete][i].libelle + ",\n";
        }
     
        
        monChamp.innerHTML = amenagements;
      } else if (propriete === "Photo préférée") {
        if(element[propriete] !== null)
        monChamp.innerHTML = "<img class='image' src='" + element[propriete] + "'/>";
      } else {
        monChamp.innerHTML = element[propriete];
      }

      if (propriete !== "userId") {
        trData.appendChild(monChamp);
      }
    }

    if (tabButtonValue.length > 0) {
      for (let i = 0; i < tabButtonValue.length; i++) {
          addButton(tabButtonValue[i], trData, idNom, lesFonction[i], url);
      }
    }

  }

}


function addButton(valeurBouton, parent, nomJsonId, functionLancee, url) {

  let td = document.createElement("td");
  parent.appendChild(td);
  let button = document.createElement("button");

  button.value = parent.id;
  button.className = "btn btn-primary";
  button.innerText = valeurBouton;
  td.className = "text-center";
  td.appendChild(button);

  button.addEventListener("click", e => {
    e.preventDefault();
    const id = e.target.value;
    let data = {};
    data[nomJsonId] = id;
    functionLancee(url, data);
  });

}

export { printTable } 