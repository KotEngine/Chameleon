# Chameleon

Android-приложение для подмены идентичности устройства (User-Agent + Client Hints) внутри встроенного WebView, с интерфейсом в стиле Liquid Glass.

## Как это работает

1. На главном экране включаешь тумблер **"Включить подмену"**.
2. Выбираешь устройство из списка ниже — оно подсвечивается зелёным.
3. Жмёшь **"Открыть браузер"** — открывается встроенный WebView, который сайту представляется выбранным устройством вместо настоящего.

Подмена работает на двух уровнях:
- HTTP-заголовок `User-Agent` — через `WebSettings.userAgentString`
- `navigator.userAgentData` / `navigator.userAgent` в JS — через инъекцию скрипта до загрузки страницы (`ClientHintsInjector.kt`), так что и `getHighEntropyValues()` в JS тоже покажет фейковое устройство

Если тумблер выключен — сайт видит настоящий User-Agent, ничего не подменяется.

## Список устройств

Зашит в `app/src/main/java/com/kotengine/chameleon/data/DeviceProfile.kt`, список `DEVICE_PROFILES`. Сейчас там:

- POCO X6 Pro
- POCO F6
- POCO F6 Pro
- Redmi Note 13 Pro+
- Samsung Galaxy S24

Чтобы добавить своё устройство — копируешь один `DeviceProfile(...)` из списка, меняешь `id`, `marketingName`, `userAgent` (реальная UA-строка нужного телефона) и `chModel`/`chPlatformVersion`/`chUaFullVersion` под него.

## Сборка через GitHub Actions

Залей проект в свой репозиторий, открой вкладку **Actions** — сборка идёт автоматически на пуш в `main`/`master`, либо запускается вручную кнопкой **Run workflow**. Готовый `app-release.apk` появится в **Artifacts** этого запуска и в **Releases** репозитория. Ключ подписи генерируется прямо в момент сборки (`.github/workflows/android.yml`), так что файл сразу ставится на телефон.

## Структура проекта

```
app/src/main/java/com/kotengine/chameleon/
├── MainActivity.kt
├── ChameleonApp.kt              — экран выбора устройства и встроенный браузер
├── data/DeviceProfile.kt        — база профилей устройств
├── webview/
│   ├── SpoofWebViewScreen.kt    — WebView с применением User-Agent
│   └── ClientHintsInjector.kt   — подмена navigator.userAgentData через JS
└── ui/glass/                    — компоненты Liquid Glass (кнопки, тумблеры, физика)
```

## Liquid Glass

Рендер стекла и физика (drag-анимации, chromatic aberration, тени) взяты из [AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass) (Apache License 2.0): `io.github.kyant0:backdrop` и `io.github.kyant0:shapes` подключены как maven-зависимости, `DragGestureInspector.kt`, `DampedDragAnimation.kt`, `InteractiveHighlight.kt`, `LiquidButton.kt`, `LiquidToggle.kt` портированы из каталога библиотеки.

## Ограничения подмены

- HTTP-заголовки `Sec-CH-UA-*` на уровне запроса не перехватываются — WebView не даёт их переотправить с новым значением. Сайты, сверяющие JS-данные с серверными заголовками (антифрод, банкинг), могут увидеть несовпадение.
- Canvas/WebGL fingerprint не подменяется — реальное железо (GPU) просвечивает через canvas fingerprinting.
