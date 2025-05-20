# 🎬 CinemaApp

## 📱 Sobre o Projeto
CinemaApp é um aplicativo Android moderno para exploração de filmes, desenvolvido com as mais recentes tecnologias do ecossistema Android. O aplicativo permite aos usuários descobrir filmes populares, bem avaliados e próximos lançamentos, além de salvar seus favoritos.

## ✨ Features do Aplicativo

- **🧭 Navegação Intuitiva**: Interface limpa e moderna com rolagem suave e navegação entre telas.
- **🍿 Catálogo de Filmes**: Visualização de filmes populares, mais bem avaliados e próximos lançamentos.
- **♾️ Scroll Infinito**: Carregamento de filmes com Paginação em forma de Scroll infinito com carregamento automático de mais conteúdo.
- **🔍 Sistema de Busca**: Pesquisa de filmes por nome ou palavras-chave.
- **📋 Detalhes dos Filmes**: Visualização de informações completas sobre cada filme.
- **❤️ Sistema de Favoritos**: Marcar e salvar filmes favoritos localmente no dispositivo.
- **🌙 Modo Escuro**: Suporte ao tema escuro para melhor experiência visual.
- **🚨 Tratamento de Erros**: Mensagens de erro informativas e botões de retry para recuperação.

## 🛠️ Features Técnicas do Aplicativo

- **🎨 Jetpack Compose**: Interface de usuário totalmente construída com o moderno toolkit declarativo da Google.
- **🏗️ Arquitetura MVVM**: Separação clara de responsabilidades para melhor manutenibilidade.
- **⚡ Kotlin Coroutines e Flow**: Programação assíncrona reativa para operações em background.
- **🔄 StateFlow**: Gerenciamento de estado reativo para UI.
- **🧭 Jetpack Navigation**: Navegação simplificada entre as telas do aplicativo.
- **💉 Hilt**: Injeção de dependência para desacoplamento de componentes.
- **💾 Room Database**: Persistência local para armazenamento de favoritos.
- **🌐 Retrofit & OkHttp**: Comunicação eficiente com APIs RESTful.
- **📦 Kotlinx Serialization**: Serialização/desserialização de JSON eficiente.
- **🖼️ Coil**: Carregamento e cache de imagens otimizados.
- **📚 Paging 3**: Carregamento paginado de dados de forma eficiente.
- **📝 Version Catalog**: Gerenciamento centralizado de dependências no Gradle.
- **🧪 Testes Unitários**: Testes automatizados para lógica de negócio com JUnit, MockK, Turbine e Truth.
- **🔬 Testes de UI**: Testes de interface do usuário com Compose UI Testing.

## 🏛️ Arquitetura e Padrões de Projeto

O CinemaApp segue uma abordagem de arquitetura semelhante ao Clean Architecture com MVVM (Model-View-ViewModel) organizada em três camadas principais:

### 📦 Camadas da Arquitetura

1. **🖼️ Presentation (UI)**
   - Componentes Compose
   - ViewModels
   - States e Events da UI

2. **⚙️ Domain**
   - Modelos de Domínio
   - Interfaces de Repository

3. **💽 Data**
   - Implementações de Repositories
   - Fontes de Dados (Remoto/API e Local/Database)
   - Modelos de Dados e Mappers

### 💉 Injeção de Dependência

O aplicativo utiliza Hilt para injeção de dependência, facilitando:
- 🔄 Gerenciamento do ciclo de vida dos objetos
- 🧩 Desacoplamento de componentes
- 🧪 Facilitação de testes substituindo implementações reais por mocks

### 📊 Diagrama da arquitetura e Fluxo de Dados

```
┌─────────────┐    ┌─────────────┐
│             │    │             │
│     UI      │◄───│  ViewModel  │
│  (Compose)  │    │   (State)   │
│             │───►│             │
└─────────────┘    └─────────────┘    
                            │
                            │
                            ▼
                    ┌─────────────┐
                    │             │
                    │ Repository  │
                    │ Interfaces  │
                    │             │
                    └─────────────┘
                            │
                            │
    ┌─-────────────────────-|────────────────────┐
    │                                            │
    ▼                                            ▼
┌─────────────┐                           ┌─────────────┐
│             │                           │             │
│  API/Remote │                           │ Local/Room  │
│  Data Source│                           │ Data Source │
│             │                           │             │
└─────────────┘                           └─────────────┘
```

## 📚 Tecnologias Usadas

### 🎨 UI e Composição
- **📱 Jetpack Compose**: 2024.09.00 - [Documentação](https://developer.android.com/jetpack/compose)
- **🎭 Material Design 3**: Integrado ao Compose - [Documentação](https://m3.material.io/)
- **🖼️ Coil Compose**: 2.5.0 - [GitHub](https://github.com/coil-kt/coil)
- **🧭 Navigation Compose**: 2.7.7 - [Documentação](https://developer.android.com/jetpack/compose/navigation)

### 🏗️ Arquitetura e Injeção de Dependência
- **📊 ViewModel**: 2.7.0 - [Documentação](https://developer.android.com/topic/libraries/architecture/viewmodel)
- **💉 Hilt**: 2.49 - [Documentação](https://developer.android.com/training/dependency-injection/hilt-android)
- **🧩 Kotlin**: 2.0.21 - [Site Oficial](https://kotlinlang.org/)
- **⚡ Coroutines**: 1.7.3 - [Documentação](https://kotlinlang.org/docs/coroutines-overview.html)
- **🌊 Flow**: Parte do Coroutines - [Documentação](https://kotlinlang.org/docs/flow.html)

### 🌐 Rede e Serialização
- **🔌 Retrofit**: 2.9.0 - [GitHub](https://github.com/square/retrofit)
- **📡 OkHttp**: 4.12.0 - [GitHub](https://github.com/square/okhttp)
- **📦 Kotlinx Serialization**: 1.6.2 - [GitHub](https://github.com/Kotlin/kotlinx.serialization)

### 💾 Persistência Local
- **🗄️ Room**: 2.6.1 - [Documentação](https://developer.android.com/training/data-storage/room)

### 📚 Paginação
- **📑 Paging 3**: 3.3.0 - [Documentação](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)

### 🧪 Testes
- **✅ JUnit**: 4.13.2 - [GitHub](https://github.com/junit-team/junit4)
- **🔍 MockK**: 1.13.7 - [GitHub](https://github.com/mockk/mockk)
- **🌀 Turbine**: 1.0.0 - [GitHub](https://github.com/cashapp/turbine)
- **⚖️ Truth**: 1.1.5 - [GitHub](https://github.com/google/truth)
- **🔬 Compose UI Test**: Integrado ao Compose - [Documentação](https://developer.android.com/jetpack/compose/testing)

## 📱 Preview do App

### 🏠 Tela Inicial
<!-- Adicionar screenshots/GIFs da tela inicial -->
![Tela Inicial](path_to_image/home_screen.png)

### 🔍 Pesquisa de Filmes
<!-- Adicionar screenshots/GIFs da funcionalidade de pesquisa -->
![Pesquisa](path_to_image/search_screen.png)

### 📋 Detalhes do Filme
<!-- Adicionar screenshots/GIFs da tela de detalhes -->
![Detalhes](path_to_image/details_screen.png)

### 🌙 Modo Escuro
<!-- Adicionar screenshots/GIFs do modo escuro -->
![Modo Escuro](path_to_image/dark_mode.png)

## 🚀 Como Executar

1. Clone o repositório
2. Abra o projeto no Android Studio
3. Configure uma chave de API do [TMDB](https://www.themoviedb.org/) no arquivo `local.properties`:
   ```
   tmdb.api.key=SUA_CHAVE_AQUI
   ```
4. Execute o aplicativo no emulador ou dispositivo

## 📄 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo LICENSE para detalhes.