import { ProdottoCarrelloDTO } from './ProdottoCarrelloDTO';

export class OrdineDTO {
  public data!: Date;
  public dettagli!: ProdottoCarrelloDTO[];

  constructor(){ }

}