const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `<custom-style>
    <style>
        vaadin-text-field, vaadin-checkbox {
            display:block;
        }
    </style>
</custom-style>`;

document.head.appendChild($_documentContainer.content);
