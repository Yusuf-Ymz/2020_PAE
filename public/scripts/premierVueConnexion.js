
import { getData } from "./utilsAPI.js";
import { homeWorker, HomeUser} from "./index.js";
import { onGetTousLesDevisList, onGetMesDevisList, onDevisListError } from "./rechercherDevis.js";


const firstViewWorker = () => {
  
  homeWorker();
  let token = localStorage.getItem("token");
  console.log("token = " + token);
  const data = {
    action: "tousLesDevis"
  }
  getData("/devis", data, token, onGetTousLesDevisList, onDevisListError);
}

//premier page que l'utilisateur voit quand il se connecte!!!
const firstViewUser = () => {
  
  HomeUser();
  let token = localStorage.getItem("token");

  const data = {
    action: "mesDevis"
  }

  getData("/devis", data, token, onGetMesDevisList, onDevisListError);

}


export { firstViewUser, firstViewWorker};