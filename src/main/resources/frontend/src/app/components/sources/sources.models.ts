export class SourceRow{
  id: number;
  name: string;
  user: string;
  passwd: string;
  host: string;
  port: number;
  authSource: string;
}

export class Error{
  nameError: string;
  userError: string;
  passwdError: string;
  hostError: string;
  portError: string;
  authSourceError: string;
}
