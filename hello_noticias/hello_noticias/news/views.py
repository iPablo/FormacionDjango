from django.forms.models import modelform_factory
from django.shortcuts import render_to_response
from django.template import RequestContext

from hello_noticias.news.models import News

# Create your views here.


def mydb(request):
    # all_news = News.objects.all()
    NewsFormSet = modelform_factory(News)
    formset = NewsFormSet()
    if formset.is_valid():
        formset.save()
    return render_to_response('news/noticias.html',
                              {'mensaje': formset},
                              # {'all': all_news}),
                              context_instance=RequestContext(request))
