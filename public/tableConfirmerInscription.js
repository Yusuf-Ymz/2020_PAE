function create_dynamic_HTML_table(
  targetHtmlElementId,
  arrayToPrint,
  confirmerInscription,
  confirmerOuvrier,
  lierUtilisateurClient,
  objectPropertiesToBeSaved
) {
  let div_container = document.getElementById(targetHtmlElementId);
  div_container.innerHTML = "";
  let myTable = document.createElement("table");
  myTable.className = "table table-bordered text-nowrap";
  div_container.appendChild(myTable);

  let headerLine = document.createElement("tr");
  myTable.appendChild(headerLine);
  const objectToPrint = arrayToPrint[0];
  for (const property in objectToPrint) {
    let th = document.createElement("th");
    th.innerHTML = property;
    headerLine.appendChild(th);
  }

  if (confirmerInscription){
    let th = document.createElement("th");
    headerLine.appendChild(th);
  } 

  if (confirmerOuvrier){
    let th = document.createElement("th");
    headerLine.appendChild(th);
  } 

  if (lierUtilisateurClient){
    let th = document.createElement("th");
    headerLine.appendChild(th);
  } 

  for (let x = 0; x < arrayToPrint.length; x++) {
    let myLine = document.createElement("tr");
    myTable.appendChild(myLine);
    const objectToPrint = arrayToPrint[x];
    for (const property in objectToPrint) {
      if (property === "id") {
        myLine.id = objectToPrint[property];
      }
      let myCell = document.createElement("td");
      myCell.innerHTML = objectToPrint[property];
      myLine.appendChild(myCell);
    }

    colonneSupp(confirmerInscription,"Confirmer inscription");
    colonneSupp(confirmerOuvrier,"Valider inscription en tant que ouvrier");
    colonneSupp(lierUtilisateurClient,"Lier l'utilisateur à un client");
  }
}

function colonneSupp(nameFunction,nameButton){
if (nameFunction) {
  let myCell = document.createElement("td");
  myLine.appendChild(myCell);
  let button = document.createElement("button");
  button.value = myLine.id;
  button.className = "btn btn-primary";
  button.innerText = nameButton;
  myCell.appendChild(button);

  button.addEventListener("click", e => {
    e.preventDefault();
    const id = e.target.value;
    const tr=e.target.parentNode.parentNode; 
    const elements = [...tr.childNodes];
    let item={};
    let i=0;
    if(objectPropertiesToBeSaved)
      {
        objectPropertiesToBeSaved.forEach(property => {
          item[property]=elements[i].innerHTML;
          i++;
        })
      }
      nameFunction(id,item);
  });
}
}
export default create_dynamic_HTML_table;
