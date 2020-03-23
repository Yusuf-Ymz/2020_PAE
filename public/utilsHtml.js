
function printTable(containerElementId, arrayToPrint, thTab,tabButtonValue = [], idNom, laFonction = "", url = "") {

  $("#carouselContent").hide();

  console.log(arrayToPrint);
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
  console.log(tabButtonValue.length);
  for (let i = 0; i < thTab.length + tabButtonValue.length; i++) {
    console.log("je pas");
    let th = document.createElement("th");
    if (i < thTab.length) {
      th.innerHTML = thTab[i];
    }

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

      if (propriete === "photoPreferee") {
        monChamp.innerHTML = "<img class='image' src='" + element[propriete] + "'/>";
      } else {
        monChamp.innerHTML = element[propriete];
      }

      if (propriete !== "userId") {
        trData.appendChild(monChamp);
      }
    }

    if (tabButtonValue.length > 0) {
      console.log(tabButtonValue.length);
      for (let i = 0; i < tabButtonValue.length; i++) {

        addButton(tabButtonValue[i], trData, idNom, laFonction, url);
      }
    }

  }

}


function addButton(valeurBouton, parent, nomJsonId, functionLancee, url) {

  let td = document.createElement("td");
  parent.appendChild(td);
  let button = document.createElement("button");
  button.value = parent.id;
  button.className = "btn_style";
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