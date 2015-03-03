from django.contrib import messages
from django.core.exceptions import ObjectDoesNotExist
from django.core.urlresolvers import reverse
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from django.template import RequestContext

from hello_noticias.news.forms import NewsForm
from hello_noticias.news.models import News


def index(request):
    return render_to_response('news/news.html',
                              context_instance=RequestContext(request))


def create(request):
    data = None
    if request.method == 'POST':
        data = request.POST
    form = NewsForm(data=data)
    if form.is_valid():
        form.save()
        messages.success(request, 'News created successfully')
        return HttpResponseRedirect(reverse('index'))
    return render_to_response('news/create.html',
                              {'form': form},
                              context_instance=RequestContext(request))


def update_get(request):
    return render_to_response('news/update_get.html',
                              context_instance=RequestContext(request))


# to do
def update(request, new_id):
    data = None
    my_new = None
    if request.method == 'POST':
        data = request.POST
    try:
        my_new = News.objects.get(id=new_id)
    except ObjectDoesNotExist:
        messages.error(request,
                       'There is no news with that ID number. Please try again'
                       )
        return HttpResponseRedirect(reverse('index'))
    form = NewsForm(data=data, instance=my_new)
    if form.is_valid():
        form.save()
        messages.success(request, 'Update completed')
        return HttpResponseRedirect(reverse('index'))
    return render_to_response('news/update.html',
                              {'form': form},
                              context_instance=RequestContext(request))
