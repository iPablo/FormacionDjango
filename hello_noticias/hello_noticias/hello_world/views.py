from django.http import HttpResponse
from django.template import Template, Context
from django.template.loader import get_template
# from django.shortcuts import render


def index(request):
    index_template = get_template('hello_world/index.html')
    hiworld = "Hello world!!!"
    html = index_template.render(Context({'mensaje': hiworld}))
    return HttpResponse(html)
