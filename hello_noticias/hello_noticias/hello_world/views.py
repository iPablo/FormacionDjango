from django.shortcuts import render_to_response
from django.template import RequestContext


def index(request):
    hiworld = "Hello world!!!"
    return render_to_response('hello_world/index.html',
                              {'mensaje': hiworld},
                              context_instance=RequestContext(request))
