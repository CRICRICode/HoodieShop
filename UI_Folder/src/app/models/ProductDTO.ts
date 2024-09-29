import { ProdottoCarrelloDTO } from "./ProdottoCarrelloDTO";

export class ProductDTO {
    nome: string = ""; // unique
    prezzo: number = -1;
    qta: number = -1;
    size: string = "";
    nomeBrand: string ="";
    url: string ="";
  
    constructor() {}

    public toString(): string {
      return JSON.stringify(this);
    }
}