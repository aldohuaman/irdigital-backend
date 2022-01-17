# Reto Intercorp Retail

# _Features_
- Crear cliente: Permite insertar un nuevo cliente con los siguientes datos:
  - nombre
  - apellido
  - email
  - dni
  - fecha
  - creación
  - fecha de nacimiento

  _Ejemplo del request_:

    - Método: POST
    - url: localhost:8080/createClient
    - payload request body
  ```javascript
  {
      "cliente": {
          "nombre": "Claudia",
          "apellido": "Gutierrez",
          "email": "claudia.gutierrez.p@gmail.com",
          "dni": "48651412",
          "fechaNacimiento" : "21-01-1988"
        }
  }
   ```
  
- Listar clientes según filtros: DNI, email o sin filtros.
  
  _Ejemplo del request_:
    - Método: GET
    - url: localhost:8080/listclients?dni=43795530
    - payload response
  ```javascript
  {
      "clientes": [
          {
            "nombre": "Aldo",
            "apellido": "Huaman Martinez",
            "email": "aldo.huaman.m@gmail.com",
            "dni": "43795530",
            "fechaCreacion": "2022-01-17T02:18:07.816+00:00",
            "fechaNacimiento": "06-10-1986"
          }
       ]
    }
   ```
- Obtener indicadores:
  - Cantidad de clientes nacidos por mes/año
  -  Mes/Año con mayor cantidad de clientes nacidos
  -  Mes/Año con menor cantidad de clientes nacidos
  -  Taza de Natalidad de cada mes

  Ejemplo del request:
     - Método: GET
     - url: localhost:8080/indicators
     - payload response
  ```javascript
  {
    "birthByMonth": {
        "10/1986": 1,
        "1/1981": 1,
        "4/1981": 2,
        "10/1990": 2,
        "1/1988": 1
    },
    "maxBirthDate": "10/1990",
    "minBirthDate": "10/1986",
    "birthRate": [
        {
            "month": "enero",
            "rate": 285.7142857142857
        },
        {
            "month": "febrero",
            "rate": 0.0
        },
        {
            "month": "marzo",
            "rate": 0.0
        },
        {
            "month": "abril",
            "rate": 285.7142857142857
        },
        {
            "month": "mayo",
            "rate": 0.0
        },
        {
            "month": "junio",
            "rate": 0.0
        },
        {
            "month": "julio",
            "rate": 0.0
        },
        {
            "month": "agosto",
            "rate": 0.0
        },
        {
            "month": "septiembre",
            "rate": 0.0
        },
        {
            "month": "octubre",
            "rate": 428.57142857142856
        },
        {
            "month": "noviembre",
            "rate": 0.0
        },
        {
            "month": "diciembre",
            "rate": 0.0
        }
    ]
  }
  ```
# _Comandos para Despliegue para AZURE_

- Cambiar ${containerRegistryServer} Por el container registry deseado (incluido en el yaml)
- Cambiar ${username} y ${password} Por el usuario y contraseña del container registry
- Cambiar ${name} por el nombre del container registry
```sh
mvn clean install -Dmaven.test.skip=true &&
az acr login --name ${name} --username ${username} --password ${password} &&
docker build -t="${containerRegistryServer}/irdigitalreto:0.0.1" --build-arg artifact_id=irdigitalreto --build-arg artifact_version=0.0.1 . &&
docker push ${containerRegistryServer}/irdigitalreto:0.0.1 &&
cd kubernetes &&
kubectl delete -f deployment.yml &&
kubectl create -f deployment.yml
```